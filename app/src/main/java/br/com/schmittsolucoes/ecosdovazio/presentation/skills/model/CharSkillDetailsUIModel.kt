package br.com.schmittsolucoes.ecosdovazio.presentation.skills.model

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.IdentifiedSkillAttribute

data class CharSkillDetailsUIModel(
    val id: String,
    val name: String,
    val description: String,
    val skillCategory: SkillCategory,
    val damage: Long?,
    val multiplier: Double?,
    val duration: Int?,
    val refreshTime: Int,
    val minLevel: Long,
    val attributes: List<IdentifiedSkillAttribute>,
    val image: Int,
    val blocked: Boolean
)
