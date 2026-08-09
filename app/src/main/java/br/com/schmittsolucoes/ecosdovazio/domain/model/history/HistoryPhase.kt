package br.com.schmittsolucoes.ecosdovazio.domain.model.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

data class HistoryPhase(
    val id: String,
    val nameTranslationId: TranslationIdentifier,
    val phaseNumber: Int,
    val mobs: List<HistoryPhaseMob>
)
