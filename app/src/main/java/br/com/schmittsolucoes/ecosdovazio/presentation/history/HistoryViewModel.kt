package br.com.schmittsolucoes.ecosdovazio.presentation.history

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.HistoryPhasesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.HistoryMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private data class HistoryInternalState(
    val errorMessage: String? = null,
)

@HiltViewModel
class HistoryViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    historyPhasesQueryUseCase: HistoryPhasesQueryUseCase,
    private val historyMapper: HistoryMapper,
) : CommonViewModel() {

    private val _internalState = MutableStateFlow(HistoryInternalState())

    val uiState: StateFlow<HistoryUIState> = combine(
        historyPhasesQueryUseCase(),
        _internalState,
    ) { phases, internalState ->
        HistoryUIState(
            phases = mapPhaseToUIModel(phases),
            actualPhaseIndex = getActualPhaseIndex(phases),
            errorMessage = internalState.errorMessage,
            isLoading = false,
        )
    }.stateInWithCommonError(
        initialValue = HistoryUIState(isLoading = true),
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _internalState.update { it.copy(errorMessage = message) }
    }

    fun onDismissErrorDialog() {
        _internalState.update { it.copy(errorMessage = null) }
    }

    private fun mapPhaseToUIModel(phases: List<CharHistoryPhase>): List<HistoryPhaseUIModel> {
        return phases.map { phase ->
            historyMapper.mapToUIModel(phase)
        }
    }

    private fun getActualPhaseIndex(phases: List<CharHistoryPhase>): Int {
        return phases.indexOf(
            element = phases.firstOrNull { it.isActual } ?: 0,
        )
    }
}
