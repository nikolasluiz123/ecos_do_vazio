package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseData
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.PhaseMobCategoryCount
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.MobPhaseInfo
import kotlinx.coroutines.flow.Flow

interface HistoryPhaseRepository {
    suspend fun save(historyPhases: List<HistoryPhase>)
    fun getPhases(charId: String, languageTag: String): Flow<List<CharHistoryPhase>>
    suspend fun getMobCategoryCountsPerPhase(): List<PhaseMobCategoryCount>
    suspend fun getExistsHistoryPhase(): Boolean
    fun getMobsFromPhase(phaseId: String, languageTag: String): Flow<List<BattleMob>>
    suspend fun getById(id: String): HistoryPhase?
    fun getHistoryPhaseDataById(phaseId: String, languageTag: String): Flow<HistoryPhaseData?>
    suspend fun getHistoryPhaseInfo(charId: String, phaseId: String): HistoryPhaseInfo?
    suspend fun saveHistoryPhaseInfo(historyPhaseInfo: HistoryPhaseInfo)
    suspend fun getPhaseMobsInfo(phaseId: String, languageTag: String): List<MobPhaseInfo>
}
