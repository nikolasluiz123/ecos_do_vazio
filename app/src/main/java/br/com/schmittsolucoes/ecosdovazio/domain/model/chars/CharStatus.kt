package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

data class CharStatus(
    val hp: Long,
    val baseDamage: Long,
    val physicalResistance: Double,
    val magicResistance: Double,
    val criticalChance: Double,
    val dodgeChance: Double,
)
