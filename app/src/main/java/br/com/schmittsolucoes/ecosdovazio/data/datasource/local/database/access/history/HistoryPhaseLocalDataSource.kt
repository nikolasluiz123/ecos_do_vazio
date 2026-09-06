package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHistoryPhaseTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.HistoryPhaseDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.LastUnfinishedHistoryPhaseTuple
import kotlinx.coroutines.flow.Flow

interface HistoryPhaseLocalDataSource : EntityLocalDataSource<HistoryPhaseEntity> {
    fun getPhases(charId: String, languageTag: String): Flow<List<CharHistoryPhaseTuple>>
    suspend fun getExistsHistoryPhase(): Boolean
    suspend fun getById(id: String): HistoryPhaseEntity?
    fun getHistoryPhaseDataById(phaseId: String, languageTag: String): Flow<HistoryPhaseDataTuple?>
    fun getLastUnfinishedHistoryPhase(charId: String, languageTag: String): Flow<LastUnfinishedHistoryPhaseTuple?>
    fun getTotalHistoryPhasesCount(): Flow<Int>
    fun getCompletedHistoryPhasesCount(charId: String): Flow<Int>
}
