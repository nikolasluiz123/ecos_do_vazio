package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharPhysicalResistanceDataTuple(
    val classCategory: ClassCategory,
    val charPhysicalResistance: Long,
    val classIncrementPhysicalResistance: Long,
    val specializationIncrementPhysicalResistance: Long?,
)
