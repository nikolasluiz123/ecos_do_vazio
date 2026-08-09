package br.com.schmittsolucoes.ecosdovazio.presentation.history

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.HistoryPhasesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    historyPhasesQueryUseCase: HistoryPhasesQueryUseCase,
    private val resourcesProvider: ResourcesProvider
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<HistoryUIState> = combine(
        historyPhasesQueryUseCase(),
        _errorMessage,
        _isLoading
    ) { phases, errorMessage, isLoading ->
        HistoryUIState(
            phases = phases.map { phase ->
                val imageResId = resourcesProvider.getPhaseImage(phase.imageName) ?: 0
                phase.toUIModel(imageResId)
            },
            errorMessage = errorMessage,
            isLoading = isLoading
        )
    }.catch { throwable ->
        onError(throwable)
        val message = getErrorMessageFrom(throwable)
        onShowErrorDialog(message)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = HistoryUIState(isLoading = true)
    )

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
