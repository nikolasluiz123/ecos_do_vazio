package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

data class CharStatus(
    val hp: Long = 0,
    val baseDamage: Long = 0,
    val physicalResistance: Double = 0.0,
    val magicResistance: Double = 0.0,
    val criticalChance: Double = 0.0,
    val dodgeChance: Double = 0.0,
)
