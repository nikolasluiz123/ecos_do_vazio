package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill

interface SkillRepository {
    suspend fun save(skills: List<Skill>)
}
