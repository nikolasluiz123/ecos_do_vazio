package br.com.schmittsolucoes.ecosdovazio.presentation.home

import android.content.Context
import androidx.lifecycle.viewModelScope
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetShowSpecializationBannerUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.CompletedHistoryPhasesCountQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.GetLastUnfinishedHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.TotalHistoryPhasesCountQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.HistoryMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    getShowSpecializationBannerUseCase: GetShowSpecializationBannerUseCase,
    completedHistoryPhasesCountQueryUseCase: CompletedHistoryPhasesCountQueryUseCase,
    totalHistoryPhasesCountQueryUseCase: TotalHistoryPhasesCountQueryUseCase,
    getLastUnfinishedHistoryPhaseUseCase: GetLastUnfinishedHistoryPhaseUseCase,
    historyMapper: HistoryMapper
) : CommonViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HomeUIState> = combine(
        getShowSpecializationBannerUseCase(),
        getLastUnfinishedHistoryPhaseUseCase(),
        completedHistoryPhasesCountQueryUseCase(),
        totalHistoryPhasesCountQueryUseCase(),
        _errorMessage
    ) { showSpecializationBanner, lastUnfinishedPhase, completedCount, totalCount, errorMessage ->
        HomeUIState(
            showSpecializationBanner = showSpecializationBanner,
            lastUnfinishedHistoryPhase = historyMapper.mapToLastUnfinishedUIModel(
                phase = lastUnfinishedPhase,
                completedPhasesCount = completedCount,
                totalPhasesCount = totalCount
            ),
            errorMessage = errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = HomeUIState()
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
