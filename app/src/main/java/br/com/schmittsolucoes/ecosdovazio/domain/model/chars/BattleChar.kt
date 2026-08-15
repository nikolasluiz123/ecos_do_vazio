package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class BattleChar(
    val level: Long,
    val name: String,
    val battleImageName: String,
    val classCategory: ClassCategory,
    val strength: CharAttribute,
    val dexterity: CharAttribute,
    val intelligence: CharAttribute,
    val physicalResistance: CharAttribute,
    val magicResistance: CharAttribute,
    val vitality: CharAttribute,
    val agility: CharAttribute,
)
