package br.com.schmittsolucoes.ecosdovazio.presentation.history

data class HistoryUIState(
    val phases: List<HistoryPhaseUIModel> = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
