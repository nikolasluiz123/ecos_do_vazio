package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharDodgeDataTuple(
    val classCategory: ClassCategory,
    val charAgility: Long,
    val classIncrementAgility: Long,
    val specializationIncrementAgility: Long?
)
