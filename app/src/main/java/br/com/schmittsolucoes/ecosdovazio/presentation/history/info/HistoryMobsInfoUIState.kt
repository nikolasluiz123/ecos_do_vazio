package br.com.schmittsolucoes.ecosdovazio.presentation.history.info

import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseDataUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseMobInfoUIModel

data class HistoryMobsInfoUIState(
    val phaseId: String = "",
    val phase: HistoryPhaseDataUIModel? = null,
    val mobsInfo: List<HistoryPhaseMobInfoUIModel> = emptyList(),
    val errorMessage: String? = null,
)
