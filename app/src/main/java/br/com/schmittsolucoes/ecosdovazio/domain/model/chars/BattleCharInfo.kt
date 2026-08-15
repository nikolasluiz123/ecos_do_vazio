package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class BattleCharInfo(
    val classCategory: ClassCategory,
    val multiplier: Double,
    val attributes: List<IdentifiedCharAttribute>,
    val actualHealth: Long
)
