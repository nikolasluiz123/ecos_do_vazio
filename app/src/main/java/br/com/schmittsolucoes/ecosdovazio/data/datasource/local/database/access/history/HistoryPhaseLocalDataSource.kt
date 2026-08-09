package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHistoryPhaseTuple
import kotlinx.coroutines.flow.Flow

interface HistoryPhaseLocalDataSource : EntityLocalDataSource<HistoryPhaseEntity> {
    fun getPhases(charId: String, languageTag: String): Flow<List<CharHistoryPhaseTuple>>
    suspend fun getExistsHistoryPhase(): Boolean
}
