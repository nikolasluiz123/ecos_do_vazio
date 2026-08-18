package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseInfoEntity

@Dao
interface HistoryPhaseInfoRoomDAO : HistoryPhaseInfoLocalDataSource, RoomLocalDataSource<HistoryPhaseInfoEntity> {
    @Query("select * from history_phase_info where char_id = :charId and phase_id = :phaseId")
    override suspend fun getByCharAndPhase(charId: String, phaseId: String): HistoryPhaseInfoEntity?
}
