package br.com.schmittsolucoes.ecosdovazio.presentation.chars

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CharAttributesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.DecrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetAvailableAttributesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharLevelInfoUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharStatusDataUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.IncrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.CharMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private data class CharInternalState(
    val errorMessage: String? = null,
)

@HiltViewModel
class CharViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    getCharLevelInfoUseCase: GetCharLevelInfoUseCase,
    getCharStatusDataUseCase: GetCharStatusDataUseCase,
    charAttributesQueryUseCase: CharAttributesQueryUseCase,
    getAvailableAttributesUseCase: GetAvailableAttributesUseCase,
    private val incrementAttributeUseCase: IncrementAttributeUseCase,
    private val decrementAttributeUseCase: DecrementAttributeUseCase,
    private val charMapper: CharMapper,
) : CommonViewModel() {

    private val _internalState = MutableStateFlow(CharInternalState())

    val uiState: StateFlow<CharUIState> = combine(
        _internalState,
        getCharLevelInfoUseCase(),
        getCharStatusDataUseCase(),
        charAttributesQueryUseCase(),
        getAvailableAttributesUseCase(),
    ) { internalState, levelInfo, charStatus, attributes, availablePoints ->
        CharUIState(
            errorMessage = internalState.errorMessage,
            levelInfo = charMapper.mapToUIModel(levelInfo),
            statusInfo = charMapper.mapToUIModel(charStatus),
            attributesInfo = charMapper.mapAttributesToUIModel(attributes, availablePoints),
            availablePoints = availablePoints,
        )
    }.stateInWithCommonError(initialValue = CharUIState())

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
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

    fun onIncrementAttribute(identifier: AttributeIdentifier) {
        launch {
            incrementAttributeUseCase(identifier)
        }
    }

    fun onDecrementAttribute(identifier: AttributeIdentifier) {
        launch {
            decrementAttributeUseCase(identifier)
        }
    }
}
