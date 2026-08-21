package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDot
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill

data class BattleMobInfo(
    val mobCategory: MobCategory,
    val offensiveMultiplier: Double = 1.0,
    val defensiveMultiplier: Double = 0.0,
    val attributes: Mob.Attributes,
    val level: Long,
    val actualHealth: Long,
    val skills: List<MobSkill> = emptyList(),
    val activeDots: List<ActiveDot> = emptyList()
)
