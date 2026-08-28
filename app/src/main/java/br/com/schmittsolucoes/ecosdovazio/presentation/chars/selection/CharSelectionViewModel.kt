package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.UserCharsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.SelectCharUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.CharMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharSelectionViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val userCharsQueryUseCase: UserCharsQueryUseCase,
    private val selectCharUseCase: SelectCharUseCase,
    private val charMapper: CharMapper
): CommonViewModel() {

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    private val _errorMessage = MutableStateFlow<String?>(null)

    private val _chars = flow {
        emitAll(userCharsQueryUseCase())
    }.map { chars ->
        mapCharSelectionToUIModel(chars)
    }

    val uiState: StateFlow<CharSelectionUIState> = combine(
        _errorMessage,
        _chars
    ) { errorMessage, chars ->
        CharSelectionUIState(
            errorMessage = errorMessage,
            chars = chars
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = CharSelectionUIState()
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
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

    fun onCharSelected(charId: String) {
        launch {
            selectCharUseCase(charId)
            _navigateToHome.value = true
        }
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }

    private fun mapCharSelectionToUIModel(chars: List<CharSelection>): List<CharSelectionUIModel> {
        return chars.map { char ->
            charMapper.mapToUIModel(char)
        }
    }
}