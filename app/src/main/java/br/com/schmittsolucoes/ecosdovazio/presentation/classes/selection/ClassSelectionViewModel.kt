package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.ClassesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CreateNewUserCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.CharException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.ClassSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.ClassMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.SkillMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private data class ClassSelectionInternalState(
    val errorMessage: String? = null,
    val selectedClassId: String? = null,
    val charName: String? = null,
    val selectedClassSkills: List<CharSkillUIModel>? = null,
    val selectedClassName: String? = null,
)

sealed interface ClassSelectionNavigationEvent {
    data object NavigateToHome : ClassSelectionNavigationEvent
}

@HiltViewModel
class ClassSelectionViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val classMapper: ClassMapper,
    private val createNewUserCharUseCase: CreateNewUserCharUseCase,
    private val classSkillsQueryUseCase: ClassSkillsQueryUseCase,
    private val skillMapper: SkillMapper,
    classesQueryUseCase: ClassesQueryUseCase
) : CommonViewModel() {

    private val _internalState = MutableStateFlow(ClassSelectionInternalState())

    private val _navigationChannel = Channel<ClassSelectionNavigationEvent>(Channel.BUFFERED)
    val navigationEvent: Flow<ClassSelectionNavigationEvent> = _navigationChannel.receiveAsFlow()

    val uiState: StateFlow<ClassSelectionUIState> = combine(
        classesQueryUseCase(),
        _internalState,
    ) { classes, internalState ->
        ClassSelectionUIState(
            classes = mapDomainToUIModelList(classes),
            errorMessage = internalState.errorMessage,
            selectedClassId = internalState.selectedClassId,
            charName = internalState.charName,
            selectedClassSkills = internalState.selectedClassSkills,
            selectedClassName = internalState.selectedClassName,
        )
    }.stateInWithCommonError(initialValue = ClassSelectionUIState())

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
        _internalState.update { it.copy(errorMessage = message) }
    }

    fun onDismissErrorDialog() {
        _internalState.update { it.copy(errorMessage = null) }
    }

    fun onSelectClass(classId: String) {
        _internalState.update { it.copy(selectedClassId = classId) }
    }

    fun onConfirmName(name: String) {
        _internalState.update { it.copy(charName = name) }

        launch {
            val selectedClassId = _internalState.value.selectedClassId
            val result = createNewUserCharUseCase(
                classId = selectedClassId,
                charName = name
            )

            result
                .onSuccess {
                    _navigationChannel.send(ClassSelectionNavigationEvent.NavigateToHome)
                }
                .onFailure { throwable ->
                    onShowCommonError(throwable)
                }
        }
    }

    fun onClassCardClick(item: SelectionItemUIModel) {
        launch {
            val skills = classSkillsQueryUseCase(item.id).first()

            val skillUIModels = skills.map {
                skillMapper.mapToUIModel(it, currentRefreshTime = 0, blocked = false)
            }

            _internalState.update {
                it.copy(
                    selectedClassSkills = skillUIModels,
                    selectedClassName = item.name
                )
            }
        }
    }

    fun onDismissSkillsBottomSheet() {
        _internalState.update {
            it.copy(
                selectedClassSkills = null,
                selectedClassName = null
            )
        }
    }

    private fun mapDomainToUIModelList(classes: List<ClassSelection>): List<SelectionItemUIModel> {
        return classes.map { classMapper.mapToUIModel(it) }
    }
}
