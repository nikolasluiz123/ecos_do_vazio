package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharBaseDamageDataTuple(
    val classCategory: ClassCategory,
    val charStrength: Long,
    val classIncrementStrength: Long,
    val specializationIncrementStrength: Long?,
    val charDexterity: Long,
    val classIncrementDexterity: Long,
    val specializationIncrementDexterity: Long?,
    val charIntelligence: Long,
    val classIncrementIntelligence: Long,
    val specializationIncrementIntelligence: Long?,
)
