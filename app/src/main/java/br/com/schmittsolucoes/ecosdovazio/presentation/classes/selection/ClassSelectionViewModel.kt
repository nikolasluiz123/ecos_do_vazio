package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.ClassesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CreateNewUserCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.CharException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.ClassSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.ClassMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.SkillMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ClassSelectionViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val classMapper: ClassMapper,
    private val createNewUserCharUseCase: CreateNewUserCharUseCase,
    private val classSkillsQueryUseCase: ClassSkillsQueryUseCase,
    private val skillMapper: SkillMapper,
    classesQueryUseCase: ClassesQueryUseCase
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _selectedClassId = MutableStateFlow<String?>(null)
    private val _charName = MutableStateFlow<String?>(null)
    private val _selectedClassSkills = MutableStateFlow<List<CharSkillUIModel>?>(null)
    private val _selectedClassName = MutableStateFlow<String?>(null)

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    private val _classes = classesQueryUseCase().map { list ->
        list.map { mapDomainToUIModel(it) }
    }

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<ClassSelectionUIState> = combine(
        _classes,
        _errorMessage,
        _selectedClassId,
        _charName,
        _selectedClassSkills,
        _selectedClassName
    ) { array ->
        ClassSelectionUIState(
            classes = array[0] as List<SelectionItemUIModel>,
            errorMessage = array[1] as String?,
            selectedClassId = array[2] as String?,
            charName = array[3] as String?,
            selectedClassSkills = array[4] as List<CharSkillUIModel>?,
            selectedClassName = array[5] as String?
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = ClassSelectionUIState()
    )

    private fun mapDomainToUIModel(classSelection: ClassSelection): SelectionItemUIModel {
        return classMapper.mapToUIModel(classSelection)
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
            is UserException.UserNotFound -> context.getString(R.string.user_error_not_found)
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

    fun onClassCardClick(item: SelectionItemUIModel) {
        launch {
            val skills = classSkillsQueryUseCase(item.id).first()

            _selectedClassSkills.value = skills.map {
                skillMapper.mapToUIModel(it, currentRefreshTime = 0, blocked = false)
            }

            _selectedClassName.value = item.name
        }
    }

    fun onDismissSkillsBottomSheet() {
        _selectedClassSkills.value = null
        _selectedClassName.value = null
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }

}
