package br.com.schmittsolucoes.ecosdovazio.domain.model.history

data class LastUnfinishedHistoryPhase(
    val phaseId: String,
    val phaseName: String,
    val tryNumber: Long,
)
