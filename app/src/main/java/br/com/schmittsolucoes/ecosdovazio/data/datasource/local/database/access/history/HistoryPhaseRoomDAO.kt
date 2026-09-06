package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHistoryPhaseTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.HistoryPhaseDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.LastUnfinishedHistoryPhaseTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryPhaseRoomDAO : HistoryPhaseLocalDataSource, RoomLocalDataSource<HistoryPhaseEntity> {

    @Query("select exists(select 1 from history_phases limit 1)")
    override suspend fun getExistsHistoryPhase(): Boolean

    @Query("select * from history_phases where id = :id")
    override suspend fun getById(id: String): HistoryPhaseEntity?

    @Query("""
        select history_phases.id as phaseId, 
               coalesce(phase_name.translated_text, phase_name_default.translated_text) as phaseName
        from history_phases
        left join translations phase_name on phase_name.id = history_phases.name_translation_id and phase_name.language_id = :languageTag
        left join translations phase_name_default on phase_name_default.id = history_phases.name_translation_id and phase_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        where history_phases.id = :phaseId
    """)
    override fun getHistoryPhaseDataById(phaseId: String, languageTag: String): Flow<HistoryPhaseDataTuple?>

    @Query("""
        select history_phases.id as phaseId, 
               coalesce(phase_name.translated_text, phase_name_default.translated_text) as phaseName, 
               history_phase_info.finished_at as finishedAt,
               (
                    history_phase_info.finished_at is null and
                    history_phases.phase_number = (
                        select min(hp.phase_number)
                        from history_phases hp
                        left join history_phase_info hpi on hpi.phase_id = hp.id and hpi.char_id = :charId
                        where hpi.finished_at is null
                    )
               ) as isActual
        from history_phases
        left join translations phase_name on phase_name.id = history_phases.name_translation_id and phase_name.language_id = :languageTag
        left join translations phase_name_default on phase_name_default.id = history_phases.name_translation_id and phase_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        left join history_phase_info on history_phase_info.phase_id = history_phases.id and history_phase_info.char_id = :charId
        order by history_phases.phase_number
    """)
    override fun getPhases(charId: String, languageTag: String): Flow<List<CharHistoryPhaseTuple>>

    @Query("""
        select history_phases.id as phaseId, 
               coalesce(phase_name.translated_text, phase_name_default.translated_text) as phaseName,
               history_phase_info.try_number as tryNumber
        from history_phases
        left join history_phase_info on history_phase_info.phase_id = history_phases.id and history_phase_info.char_id = :charId
        left join translations phase_name on phase_name.id = history_phases.name_translation_id and phase_name.language_id = :languageTag
        left join translations phase_name_default on phase_name_default.id = history_phases.name_translation_id and phase_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        where history_phase_info.finished_at is null
        order by history_phases.phase_number asc
        limit 1
    """)
    override fun getLastUnfinishedHistoryPhase(charId: String, languageTag: String): Flow<LastUnfinishedHistoryPhaseTuple?>

    @Query("select count(*) from history_phases")
    override fun getTotalHistoryPhasesCount(): Flow<Int>

    @Query("""
        select count(*)
        from history_phases
        inner join history_phase_info on history_phase_info.phase_id = history_phases.id and history_phase_info.char_id = :charId
        where history_phase_info.finished_at is not null
    """)
    override fun getCompletedHistoryPhasesCount(charId: String): Flow<Int>
}
