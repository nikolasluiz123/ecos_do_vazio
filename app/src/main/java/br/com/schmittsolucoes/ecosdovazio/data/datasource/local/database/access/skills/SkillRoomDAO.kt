package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.skills

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.SkillEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSkillTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.MobSkillTuple
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillRoomDAO : SkillLocalDataSource, RoomLocalDataSource<SkillEntity> {

    @Query("""
        select skills.id as id,
               coalesce(skill_name.translated_text, skill_name_default.translated_text) as name,
               coalesce(skill_description.translated_text, skill_description_default.translated_text) as description,
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
        left join translations skill_name on skill_name.id = skills.name_translation_id and skill_name.language_id = :languageTag
        left join translations skill_name_default on skill_name_default.id = skills.name_translation_id and skill_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        left join translations skill_description on skill_description.id = skills.description_translation_id and skill_description.language_id = :languageTag
        left join translations skill_description_default on skill_description_default.id = skills.description_translation_id and skill_description_default.language_id = (select id from languages where is_default = 1 limit 1)
        where (
            skills.class_id = :classId 
            or (skills.specialization_id is not null and skills.specialization_id = :specializationId)
        )
        and skills.skill_category in (:categories)
        order by skills.min_level
    """)
    override fun getCharSkills(
        languageTag: String,
        classId: String,
        specializationId: String?,
        categories: List<SkillCategory>
    ): Flow<List<CharSkillTuple>>

    @Query("""
        select skills.id as id,
               coalesce(skill_name.translated_text, skill_name_default.translated_text) as name,
               coalesce(skill_description.translated_text, skill_description_default.translated_text) as description,
               skills.skill_category as skillCategory,
               skills.damage as damage,
               skills.multiplier as multiplier,
               skills.duration as duration,
               skills.refresh_time as refreshTime,
               skills.min_level as minLevel,
               skills.image_name as imageName
        from skills
        left join translations skill_name on skill_name.id = skills.name_translation_id and skill_name.language_id = :languageTag
        left join translations skill_name_default on skill_name_default.id = skills.name_translation_id and skill_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        left join translations skill_description on skill_description.id = skills.description_translation_id and skill_description.language_id = :languageTag
        left join translations skill_description_default on skill_description_default.id = skills.description_translation_id and skill_description_default.language_id = (select id from languages where is_default = 1 limit 1)
        where skills.mob_id = :mobId
        order by skills.min_level
    """)
    override suspend fun getMobSkills(languageTag: String, mobId: String): List<MobSkillTuple>

    @Query("""
        select skills.id as id,
               coalesce(skill_name.translated_text, skill_name_default.translated_text) as name,
               coalesce(skill_description.translated_text, skill_description_default.translated_text) as description,
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
        left join translations skill_name on skill_name.id = skills.name_translation_id and skill_name.language_id = :languageTag
        left join translations skill_name_default on skill_name_default.id = skills.name_translation_id and skill_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        left join translations skill_description on skill_description.id = skills.description_translation_id and skill_description.language_id = :languageTag
        left join translations skill_description_default on skill_description_default.id = skills.description_translation_id and skill_description_default.language_id = (select id from languages where is_default = 1 limit 1)
        where skills.class_id = :classId
        or (skills.specialization_id is not null and skills.specialization_id = :specializationId)
        order by skills.min_level
    """)
    override fun getAllSkills(languageTag: String, classId: String, specializationId: String?): Flow<List<CharSkillTuple>>
}
