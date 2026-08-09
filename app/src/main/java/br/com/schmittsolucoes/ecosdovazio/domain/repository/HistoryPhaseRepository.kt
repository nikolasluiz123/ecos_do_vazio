package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.PhaseMobCategoryCount
import kotlinx.coroutines.flow.Flow

interface HistoryPhaseRepository {
    suspend fun save(historyPhases: List<HistoryPhase>)
    fun getPhases(charId: String, languageTag: String): Flow<List<CharHistoryPhase>>
    suspend fun getMobCategoryCountsPerPhase(): List<PhaseMobCategoryCount>
    suspend fun getExistsHistoryPhase(): Boolean
}
