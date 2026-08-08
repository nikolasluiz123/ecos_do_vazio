package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharHealthDataTuple(
    val classCategory: ClassCategory,
    val charVitality: Long,
    val classIncrementVitality: Long,
    val specializationIncrementVitality: Long?,
)
