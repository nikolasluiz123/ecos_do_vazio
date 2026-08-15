package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.skills

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.SkillEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSkillTuple
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillRoomDAO : SkillLocalDataSource, RoomLocalDataSource<SkillEntity> {

    @Query("""
        select skills.id as id,
               skills.name_translation_id as name,
               skills.description_translation_id as description,
               skills.skill_category as skillCategory,
               skills.damage as damage,
               skills.multiplier as multiplier,
               skills.duration as duration,
               skills.refresh_time as refreshTime,
               skills.min_level as minLevel,
               skills.required_strength as requiredStrength,
               skills.required_dexterity as requiredDexterity,
               skills.required_intelligence as requiredIntelligence,
               skills.required_physical_resistance as requiredPhysicalResistance,
               skills.required_magic_resistance as requiredMagicResistance,
               skills.required_vitality as requiredVitality,
               skills.required_agility as requiredAgility,
               skills.image_name as imageName
        from skills
        where (
            skills.class_id = :classId 
            or (skills.specialization_id is not null and skills.specialization_id = :specializationId)
        )
        and skills.skill_category in (:categories)
        order by skills.min_level
    """)
    override fun getCharSkills(
        classId: String,
        specializationId: String?,
        categories: List<SkillCategory>
    ): Flow<List<CharSkillTuple>>
}
