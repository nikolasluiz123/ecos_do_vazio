package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharCriticalDataTuple(
    val classCategory: ClassCategory,
    val charDexterity: Long,
    val classIncrementDexterity: Long,
    val specializationIncrementDexterity: Long?
)
