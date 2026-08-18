package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseInfoEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseMobEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHistoryPhaseTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.PhaseMobCategoryCountTuple
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.PhaseMobCategoryCount

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

fun HistoryPhaseInfoEntity.toDomain() = HistoryPhaseInfo(
    id = id,
    charId = charId,
    phaseId = phaseId,
    finishedAt = finishedAt,
    tryNumber = tryNumber
)

fun HistoryPhaseInfo.toEntity() = HistoryPhaseInfoEntity(
    id = id,
    charId = charId,
    phaseId = phaseId,
    finishedAt = finishedAt,
    tryNumber = tryNumber
)

fun CharHistoryPhaseTuple.toDomain(imageName: String) = CharHistoryPhase(
    phaseId = phaseId,
    phaseName = phaseName,
    finishedAt = finishedAt,
    isActual = isActual,
    imageName = imageName
)

fun PhaseMobCategoryCountTuple.toDomain() = PhaseMobCategoryCount(
    historyPhaseId = historyPhaseId,
    mobCategory = mobCategory,
    count = count
)
