package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseMobEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.PhaseMobCategoryCountTuple

interface HistoryPhaseMobLocalDataSource : EntityLocalDataSource<HistoryPhaseMobEntity> {
    suspend fun getMobCategoryCountsPerPhase(): List<PhaseMobCategoryCountTuple>
}
