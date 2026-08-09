package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.ClassesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CreateNewUserCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.CharException
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ClassSelectionViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val resourcesProvider: ResourcesProvider,
    private val createNewUserCharUseCase: CreateNewUserCharUseCase,
    classesQueryUseCase: ClassesQueryUseCase
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _selectedClassId = MutableStateFlow<String?>(null)
    private val _charName = MutableStateFlow<String?>(null)

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    private val _classes = classesQueryUseCase().map { list ->
        list.map { mapDomainToUIModel(it) }
    }

    val uiState: StateFlow<ClassSelectionUIState> = combine(
        _classes,
        _errorMessage,
        _selectedClassId,
        _charName
    ) { classes, errorMessage, selectedClassId, charName ->
        ClassSelectionUIState(
            classes = classes,
            errorMessage = errorMessage,
            selectedClassId = selectedClassId,
            charName = charName
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = ClassSelectionUIState()
    )

    private fun mapDomainToUIModel(classSelection: ClassSelection): ClassSelectionUIModel {
        val presentationDrawableId = resourcesProvider.getClassImage(classSelection.presentationImageName)!!
        return classSelection.toUIModel(presentationDrawableId)
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
            is CharException.ClassSelectionRequired -> context.getString(R.string.char_error_class_required)
            is CharException.NameRequired -> context.getString(R.string.char_error_name_required)
            is CharException.DuplicatedName -> context.getString(
                R.string.char_error_duplicated_name,
                throwable.name
            )
            is CharException.NameTooLong -> context.getString(
                R.string.char_error_name_too_long,
                throwable.maxLength
            )
            else -> context.getString(R.string.error_unexpected)
        }
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onSelectClass(classId: String) {
        _selectedClassId.value = classId
    }

    fun onConfirmName(name: String) {
        _charName.value = name

        launch {
            val result = createNewUserCharUseCase.invoke(
                classId = _selectedClassId.value,
                charName = _charName.value
            )

            result
                .onSuccess { _navigateToHome.value = true }
                .onFailure { onShowErrorDialog(getErrorMessageFrom(it)) }
        }
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }

}
