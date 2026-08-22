package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDoT
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class BattleCharInfo(
    val classCategory: ClassCategory,
    val offensiveMultiplier: Double = 1.0,
    val defensiveMultiplier: Double = 0.0,
    val attributes: List<IdentifiedCharAttribute>,
    val actualHealth: Long,
    val activeDots: List<ActiveDoT.MobActiveDoT> = emptyList()
)
