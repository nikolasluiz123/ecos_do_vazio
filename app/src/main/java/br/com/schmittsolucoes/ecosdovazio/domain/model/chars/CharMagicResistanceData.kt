package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharMagicResistanceData(
    val classCategory: ClassCategory,
    val magicResistance: CharAttribute,
)
