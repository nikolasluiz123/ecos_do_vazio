package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharPhysicalResistanceData(
    val classCategory: ClassCategory,
    val physicalResistance: CharAttribute,
)
