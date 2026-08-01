package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CharSelectionViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
): CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<CharSelectionUIState> = combine(
        _errorMessage
    ) { errorMessage ->
        CharSelectionUIState(
            errorMessage = errorMessage.firstOrNull()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = CharSelectionUIState()
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

}