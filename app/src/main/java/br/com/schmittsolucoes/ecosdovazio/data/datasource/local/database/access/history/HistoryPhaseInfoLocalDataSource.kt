package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseInfoEntity

interface HistoryPhaseInfoLocalDataSource : EntityLocalDataSource<HistoryPhaseInfoEntity> {
    suspend fun getByCharAndPhase(charId: String, phaseId: String): HistoryPhaseInfoEntity?
}
