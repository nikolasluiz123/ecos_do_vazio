package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

data class LastUnfinishedHistoryPhaseTuple(
    val phaseId: String,
    val phaseName: String,
    val tryNumber: Long?,
)
