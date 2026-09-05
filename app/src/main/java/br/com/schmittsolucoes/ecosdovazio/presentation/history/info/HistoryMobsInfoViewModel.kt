package br.com.schmittsolucoes.ecosdovazio.presentation.history.info

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.GetPhaseDataByIdUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.HistoryPhaseInfoQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseMobInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.navigation.HistoryMobsInfoRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.HistoryMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.MobMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryMobsInfoViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
    getPhaseDataByIdUseCase: GetPhaseDataByIdUseCase,
    historyPhaseInfoQueryUseCase: HistoryPhaseInfoQueryUseCase,
    private val mobMapper: MobMapper,
    private val historyMapper: HistoryMapper,
) : CommonViewModel() {

    private val route = savedStateHandle.toRoute<HistoryMobsInfoRoute>()
    val phaseId: String = route.phaseId

    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<HistoryMobsInfoUIState> = combine(
        getPhaseDataByIdUseCase(phaseId),
        historyPhaseInfoQueryUseCase(phaseId),
        _errorMessage,
    ) { phase, mobsInfo, errorMessage ->
        HistoryMobsInfoUIState(
            phaseId = phaseId,
            phase = phase?.let(historyMapper::mapToInfoUIModel),
            mobsInfo = mapMobsInfoToUIModel(mobsInfo),
            errorMessage = errorMessage,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = HistoryMobsInfoUIState(phaseId = phaseId),
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

    private fun mapMobsInfoToUIModel(mobsInfo: List<HistoryPhaseMobInfo>): List<HistoryPhaseMobInfoUIModel> {
        return mobsInfo.map(mobMapper::mapToUIModel)
    }
}
