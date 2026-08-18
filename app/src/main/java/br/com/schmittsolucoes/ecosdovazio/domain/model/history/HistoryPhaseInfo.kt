package br.com.schmittsolucoes.ecosdovazio.domain.model.history

import java.time.Instant

data class HistoryPhaseInfo(
    val id: String,
    val charId: String,
    val phaseId: String,
    val finishedAt: Instant? = null,
    val tryNumber: Long,
)
