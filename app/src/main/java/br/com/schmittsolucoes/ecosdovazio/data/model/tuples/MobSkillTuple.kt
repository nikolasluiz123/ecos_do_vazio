package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

data class MobSkillTuple(
    val id: String,
    val name: String,
    val description: String,
    val imageName: String,
    val skillCategory: SkillCategory,
    val damage: Long?,
    val lifeRestore: Long?,
    val multiplier: Double?,
    val duration: Int?,
    val refreshTime: Int,
    val minLevel: Long,
)
