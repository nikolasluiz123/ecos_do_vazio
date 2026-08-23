package br.com.schmittsolucoes.ecosdovazio.presentation.history

data class HistoryUIState(
    val phases: List<HistoryPhaseUIModel> = emptyList(),
    val actualPhaseIndex: Int = 0,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
