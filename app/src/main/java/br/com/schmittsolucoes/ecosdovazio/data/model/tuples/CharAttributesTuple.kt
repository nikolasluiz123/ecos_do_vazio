package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

data class CharAttributesTuple(
    val charStrength: Long,
    val classIncrementStrength: Long,
    val specializationIncrementStrength: Long?,

    val charDexterity: Long,
    val classIncrementDexterity: Long,
    val specializationIncrementDexterity: Long?,

    val charIntelligence: Long,
    val classIncrementIntelligence: Long,
    val specializationIncrementIntelligence: Long?,

    val charPhysicalResistance: Long,
    val classIncrementPhysicalResistance: Long,
    val specializationIncrementPhysicalResistance: Long?,

    val charMagicResistance: Long,
    val classIncrementMagicResistance: Long,
    val specializationIncrementMagicResistance: Long?,

    val charVitality: Long,
    val classIncrementVitality: Long,
    val specializationIncrementVitality: Long?,

    val charAgility: Long,
    val classIncrementAgility: Long,
    val specializationIncrementAgility: Long?,
)
