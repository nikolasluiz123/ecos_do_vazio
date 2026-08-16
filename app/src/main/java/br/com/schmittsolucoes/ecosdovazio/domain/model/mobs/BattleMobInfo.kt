package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

data class BattleMobInfo(
    val mobCategory: MobCategory,
    val offensiveMultiplier: Double = 1.0,
    val defensiveMultiplier: Double = 0.0,
    val attributes: Mob.Attributes,
    val level: Long,
    val actualHealth: Long
)
