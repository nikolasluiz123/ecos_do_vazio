package br.com.schmittsolucoes.ecosdovazio.presentation.home

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetShowSpecializationBannerUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.GetCompletedHistoryPhasesCountQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.GetLastUnfinishedHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.TotalHistoryPhasesCountQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.HistoryMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private data class HomeInternalState(
    val errorMessage: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    getShowSpecializationBannerUseCase: GetShowSpecializationBannerUseCase,
    getCompletedHistoryPhasesCountQueryUseCase: GetCompletedHistoryPhasesCountQueryUseCase,
    totalHistoryPhasesCountQueryUseCase: TotalHistoryPhasesCountQueryUseCase,
    getLastUnfinishedHistoryPhaseUseCase: GetLastUnfinishedHistoryPhaseUseCase,
    historyMapper: HistoryMapper,
) : CommonViewModel() {

    private val _internalState = MutableStateFlow(HomeInternalState())

    val uiState: StateFlow<HomeUIState> = combine(
        getShowSpecializationBannerUseCase(),
        getLastUnfinishedHistoryPhaseUseCase(),
        getCompletedHistoryPhasesCountQueryUseCase(),
        totalHistoryPhasesCountQueryUseCase(),
        _internalState,
    ) { showSpecializationBanner, lastUnfinishedPhase, completedCount, totalCount, internalState ->
        HomeUIState(
            showSpecializationBanner = showSpecializationBanner,
            lastUnfinishedHistoryPhase = historyMapper.mapToLastUnfinishedUIModel(
                phase = lastUnfinishedPhase,
                completedPhasesCount = completedCount,
                totalPhasesCount = totalCount,
            ),
            errorMessage = internalState.errorMessage,
        )
    }.stateInWithCommonError(
        initialValue = HomeUIState(),
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
}
