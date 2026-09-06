package br.com.schmittsolucoes.ecosdovazio.presentation.home

import br.com.schmittsolucoes.ecosdovazio.presentation.history.model.LastUnfinishedHistoryPhaseUIModel

data class HomeUIState(
    val showSpecializationBanner: Boolean = false,
    val lastUnfinishedHistoryPhase: LastUnfinishedHistoryPhaseUIModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
