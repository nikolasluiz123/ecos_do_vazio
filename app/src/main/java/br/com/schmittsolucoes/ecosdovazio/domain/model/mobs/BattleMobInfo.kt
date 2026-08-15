package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

data class BattleMobInfo(
    val mobCategory: MobCategory,
    val multiplier: Double,
    val attributes: Mob.Attributes,
    val actualHealth: Long
)
