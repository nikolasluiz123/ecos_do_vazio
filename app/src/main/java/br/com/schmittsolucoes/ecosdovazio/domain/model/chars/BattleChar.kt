package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class BattleChar(
    val level: Long,
    val name: String,
    val battleImageName: String,
    val classCategory: ClassCategory,
    val strength: Attribute,
    val dexterity: Attribute,
    val intelligence: Attribute,
    val physicalResistance: Attribute,
    val magicResistance: Attribute,
    val vitality: Attribute,
    val agility: Attribute,
) {
    data class Attribute(
        val charValue: Long,
        val classValue: Long,
        val specializationValue: Long?,
    ) {
        val value: Long
            get() = charValue + classValue + (specializationValue ?: 0L)
    }
}
