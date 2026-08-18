package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseInfoLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseMobLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toDomain
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.PhaseMobCategoryCount
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryPhaseRepositoryImpl @Inject constructor(
    private val historyPhaseLocalDataSource: HistoryPhaseLocalDataSource,
    private val historyPhaseMobLocalDataSource: HistoryPhaseMobLocalDataSource,
    private val historyPhaseInfoLocalDataSource: HistoryPhaseInfoLocalDataSource,
    private val databaseTransaction: DatabaseTransaction
): HistoryPhaseRepository {

    override suspend fun save(historyPhases: List<HistoryPhase>) {
        check(databaseTransaction.isInTransaction()) {
            "HistoryPhaseRepository.save must be called within a database transaction."
        }

        val phaseEntities = historyPhases.map { it.toEntity() }
        val mobEntities = historyPhases.flatMap { phase ->
            phase.mobs.map { it.toEntity() }
        }

        historyPhaseLocalDataSource.upsert(phaseEntities)
        historyPhaseMobLocalDataSource.upsert(mobEntities)
    }

    override fun getPhases(charId: String, languageTag: String): Flow<List<CharHistoryPhase>> {
        return historyPhaseLocalDataSource.getPhases(charId, languageTag).map { list ->
            list.map { it.toDomain("") }
        }
    }

    override suspend fun getMobCategoryCountsPerPhase(): List<PhaseMobCategoryCount> {
        return historyPhaseMobLocalDataSource.getMobCategoryCountsPerPhase().map { it.toDomain() }
    }

    override suspend fun getExistsHistoryPhase(): Boolean {
        return historyPhaseLocalDataSource.getExistsHistoryPhase()
    }

    override fun getMobsFromPhase(phaseId: String, languageTag: String): Flow<List<BattleMob>> {
        return historyPhaseMobLocalDataSource.getMobsFromPhase(phaseId, languageTag).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: String): HistoryPhase? {
        return historyPhaseLocalDataSource.getById(id)?.toDomain(emptyList())
    }

    override suspend fun getHistoryPhaseInfo(charId: String, phaseId: String): HistoryPhaseInfo? {
        return historyPhaseInfoLocalDataSource.getByCharAndPhase(charId, phaseId)?.toDomain()
    }

    override suspend fun saveHistoryPhaseInfo(historyPhaseInfo: HistoryPhaseInfo) {
        historyPhaseInfoLocalDataSource.upsert(listOf(historyPhaseInfo.toEntity()))
    }
}
