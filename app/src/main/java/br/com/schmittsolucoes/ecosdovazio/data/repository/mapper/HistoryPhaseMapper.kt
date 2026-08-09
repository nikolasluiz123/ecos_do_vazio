package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseMobEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseMob

fun HistoryPhase.toEntity() = HistoryPhaseEntity(
    id = id,
    nameTranslationId = nameTranslationId.name,
    phaseNumber = phaseNumber
)

fun HistoryPhaseMob.toEntity() = HistoryPhaseMobEntity(
    id = id,
    mobId = mobId,
    historyPhaseId = historyPhaseId
)

fun HistoryPhaseEntity.toDomain(mobs: List<HistoryPhaseMobEntity>) = HistoryPhase(
    id = id,
    nameTranslationId = TranslationIdentifier.valueOf(nameTranslationId),
    phaseNumber = phaseNumber,
    mobs = mobs.map { it.toDomain() }
)

fun HistoryPhaseMobEntity.toDomain() = HistoryPhaseMob(
    id = id,
    mobId = mobId,
    historyPhaseId = historyPhaseId
)
