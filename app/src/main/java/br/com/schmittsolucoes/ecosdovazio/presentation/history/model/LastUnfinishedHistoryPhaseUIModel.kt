package br.com.schmittsolucoes.ecosdovazio.presentation.history.model

data class LastUnfinishedHistoryPhaseUIModel(
    val phaseId: String? = null,
    val phaseName: String = "",
    val tryNumber: Long = 0,
    val completedPhasesCount: Int = 0,
    val totalPhasesCount: Int = 0,
    val progress: Float = 0f,
    val isCompleted: Boolean = false
)
