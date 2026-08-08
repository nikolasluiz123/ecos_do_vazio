package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharMagicResistanceDataTuple(
    val classCategory: ClassCategory,
    val charMagicResistance: Long,
    val classIncrementMagicResistance: Long,
    val specializationIncrementMagicResistance: Long?,
)
