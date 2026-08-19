package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill

data class BattleMob(
    val mobId: String,
    val phaseMobId: String,
    val name: String,
    val description: String,
    val imageName: String,
    val mobCategory: MobCategory,
    val level: Long = 1,
    val offensiveMultiplier: Double = 1.0,
    val defensiveMultiplier: Double = 0.0,
    val actualHealth: Long = 0,
    val attributes: Mob.Attributes,
    val skills: List<MobSkill> = emptyList()
)
