package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseMobLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import javax.inject.Inject

class HistoryPhaseRepositoryImpl @Inject constructor(
    private val historyPhaseLocalDataSource: HistoryPhaseLocalDataSource,
    private val historyPhaseMobLocalDataSource: HistoryPhaseMobLocalDataSource,
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
}
