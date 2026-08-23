package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.skills.SkillLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toDomain
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toDomainDetails
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkillDetails
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SkillRepositoryImpl @Inject constructor(
    private val skillLocalDataSource: SkillLocalDataSource,
): SkillRepository {
    override suspend fun save(skills: List<Skill>) {
        val skillEntities = skills.map { it.toEntity() }
        skillLocalDataSource.upsert(skillEntities)
    }

    override fun getCharSkills(
        languageTag: String,
        classId: String,
        specializationId: String?,
        categories: List<SkillCategory>
    ): Flow<List<CharSkill>> {
        return skillLocalDataSource.getCharSkills(
            languageTag = languageTag,
            classId = classId,
            specializationId = specializationId,
            categories = categories
        ).map { skills -> skills.map { it.toDomain() } }
    }

    override suspend fun getMobSkills(languageTag: String, mobId: String): List<MobSkill> {
        return skillLocalDataSource.getMobSkills(languageTag, mobId).map { it.toDomain() }
    }

    override fun getAllSkills(
        languageTag: String,
        classId: String,
        specializationId: String?
    ): Flow<List<CharSkillDetails>> {
        return skillLocalDataSource.getAllSkills(
            languageTag = languageTag,
            classId = classId,
            specializationId = specializationId
        ).map { skills -> skills.map { it.toDomainDetails() } }
    }
}
