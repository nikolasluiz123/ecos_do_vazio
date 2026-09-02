package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseMobEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.BattleMobTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.PhaseMobCategoryCountTuple
import kotlinx.coroutines.flow.Flow

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

    @Query("""
        select 
            mobs.id as mobId,
            history_phase_mobs.id as phaseMobId,
            mobs.strength as strength,
            mobs.dexterity as dexterity,
            mobs.intelligence as intelligence,
            mobs.physical_resistance as physicalResistance,
            mobs.magic_resistance as magicResistance,
            mobs.vitality as vitality,
            mobs.agility as agility,
            mobs.battle_image_name as battleImageName,
            mobs.mob_category as mobCategory,
            t_name.translated_text as name,
            t_desc.translated_text as description
        from history_phases
        join history_phase_mobs on history_phase_mobs.history_phase_id = history_phases.id
        join mobs on mobs.id = history_phase_mobs.mob_id
        join translations t_name on t_name.id = mobs.name_translation_id and t_name.language_id = :languageId
        join translations t_desc on t_desc.id = mobs.description_translation_id and t_desc.language_id = :languageId
        where history_phases.id = :phaseId
    """)
    override fun getMobsFromPhase(phaseId: String, languageId: String): Flow<List<BattleMobTuple>>
}
