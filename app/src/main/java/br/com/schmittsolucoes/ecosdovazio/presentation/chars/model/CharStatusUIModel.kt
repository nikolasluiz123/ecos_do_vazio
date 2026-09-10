package br.com.schmittsolucoes.ecosdovazio.presentation.chars.model

data class CharStatusUIModel(
    val hp: String = "0",
    val baseDamage: String = "0",
    val physicalResistance: String = "0,00%",
    val magicResistance: String = "0,00%",
    val criticalChance: String = "0,00%",
    val dodgeChance: String = "0,00%"
)
