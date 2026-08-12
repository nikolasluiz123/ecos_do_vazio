package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseMobEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.BattleMobTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.PhaseMobCategoryCountTuple
import kotlinx.coroutines.flow.Flow

interface HistoryPhaseMobLocalDataSource : EntityLocalDataSource<HistoryPhaseMobEntity> {
    suspend fun getMobCategoryCountsPerPhase(): List<PhaseMobCategoryCountTuple>
    fun getMobsFromPhase(phaseId: String, languageId: String): Flow<List<BattleMobTuple>>
}
