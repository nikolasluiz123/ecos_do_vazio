package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.skills

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.SkillEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSkillTuple
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import kotlinx.coroutines.flow.Flow

interface SkillLocalDataSource : EntityLocalDataSource<SkillEntity> {
    fun getCharSkills(
        classId: String,
        specializationId: String?,
        categories: List<SkillCategory>
    ): Flow<List<CharSkillTuple>>
}
