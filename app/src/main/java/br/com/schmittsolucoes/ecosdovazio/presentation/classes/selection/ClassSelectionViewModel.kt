package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.ClassesQueryUseCase
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
    classesQueryUseCase: ClassesQueryUseCase
): CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)

    private val _classes = classesQueryUseCase().map { list ->
        list.map { mapDomainToUIModel(it) }
    }

    val uiState: StateFlow<ClassSelectionUIState> = combine(
        _classes,
        _errorMessage
    ) { classes, errorMessage ->
        ClassSelectionUIState(
            classes = classes,
            errorMessage = errorMessage
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
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

}
