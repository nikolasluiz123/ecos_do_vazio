package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

data class CharSkillDetails(
    val id: String,
    val name: String,
    val description: String,
    val skillCategory: SkillCategory,
    val damage: Long?,
    val multiplier: Double?,
    val duration: Int?,
    val refreshTime: Int,
    val minLevel: Long,
    val attributes: CharSkill.Attributes,
    val imageName: String,
    val blocked: Boolean
)
