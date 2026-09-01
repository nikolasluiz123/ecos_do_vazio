package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

data class ProjectedDamageInfo(
    val totalDamage: Long,
    val baseDamage: Long,
    val attributeBonus: Long,
    val buffBonus: Long,
    val defenseReduction: Long,
    val targetBuffReduction: Long
)
