package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import java.time.Instant

data class CharHistoryPhaseTuple(
    val phaseId: String,
    val phaseName: String,
    val finishedAt: Instant?,
    val isActual: Boolean
)
