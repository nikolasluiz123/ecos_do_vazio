package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.UserCharsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.SelectCharUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.CharMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private data class CharSelectionInternalState(
    val errorMessage: String? = null,
)

sealed interface CharSelectionNavigationEvent {
    data object NavigateToHome : CharSelectionNavigationEvent
}

@HiltViewModel
class CharSelectionViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val selectCharUseCase: SelectCharUseCase,
    private val charMapper: CharMapper,
    userCharsQueryUseCase: UserCharsQueryUseCase,
): CommonViewModel() {

    private val _internalState = MutableStateFlow(CharSelectionInternalState())

    private val _navigationChannel = Channel<CharSelectionNavigationEvent>(Channel.BUFFERED)
    val navigationEvent: Flow<CharSelectionNavigationEvent> = _navigationChannel.receiveAsFlow()

    val uiState: StateFlow<CharSelectionUIState> = combine(
        _internalState,
        userCharsQueryUseCase(),
    ) { internalState, chars ->
        CharSelectionUIState(
            errorMessage = internalState.errorMessage,
            chars = mapCharSelectionToUIModel(chars),
        )
    }.stateInWithCommonError(initialValue = CharSelectionUIState())

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

    fun onCharSelected(charId: String) {
        launch {
            selectCharUseCase(charId)
            _navigationChannel.send(CharSelectionNavigationEvent.NavigateToHome)
        }
    }

    private fun mapCharSelectionToUIModel(chars: List<CharSelection>): List<CharSelectionUIModel> {
        return chars.map { char ->
            charMapper.mapToUIModel(char)
        }
    }
}
