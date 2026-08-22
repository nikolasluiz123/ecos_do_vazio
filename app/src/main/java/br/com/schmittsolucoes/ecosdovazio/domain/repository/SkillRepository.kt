package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import kotlinx.coroutines.flow.Flow

interface SkillRepository {
    suspend fun save(skills: List<Skill>)

    fun getCharSkills(
        languageTag: String,
        classId: String,
        specializationId: String?,
        categories: List<SkillCategory>
    ): Flow<List<CharSkill>>

    suspend fun getMobSkills(languageTag: String, mobId: String): List<MobSkill>
}
