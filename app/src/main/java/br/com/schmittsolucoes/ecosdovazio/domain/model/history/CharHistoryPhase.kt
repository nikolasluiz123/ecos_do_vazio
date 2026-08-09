package br.com.schmittsolucoes.ecosdovazio.domain.model.history

import java.time.Instant

data class CharHistoryPhase(
    val phaseId: String,
    val phaseName: String,
    val finishedAt: Instant?,
    val isActual: Boolean,
    val imageName: String
)
