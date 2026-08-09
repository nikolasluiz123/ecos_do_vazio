package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.skills.SkillLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import javax.inject.Inject

class SkillRepositoryImpl @Inject constructor(
    private val skillLocalDataSource: SkillLocalDataSource,
): SkillRepository {
    override suspend fun save(skills: List<Skill>) {
        val skillEntities = skills.map { it.toEntity() }
        skillLocalDataSource.upsert(skillEntities)
    }
}
