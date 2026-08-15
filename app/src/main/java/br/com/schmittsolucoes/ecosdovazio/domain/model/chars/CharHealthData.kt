package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharHealthData(
    val classCategory: ClassCategory,
    val vitality: CharAttribute,
)
