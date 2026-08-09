package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseMobEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.PhaseMobCategoryCountTuple

@Dao
interface HistoryPhaseMobRoomDAO : HistoryPhaseMobLocalDataSource, RoomLocalDataSource<HistoryPhaseMobEntity> {

    @Query("""
        select history_phase_mobs.history_phase_id as historyPhaseId, 
               mobs.mob_category as mobCategory, 
               count(*) as count
        from history_phase_mobs
        join mobs on history_phase_mobs.mob_id = mobs.id
        group by history_phase_mobs.history_phase_id, mobs.mob_category
    """)
    override suspend fun getMobCategoryCountsPerPhase(): List<PhaseMobCategoryCountTuple>
}
