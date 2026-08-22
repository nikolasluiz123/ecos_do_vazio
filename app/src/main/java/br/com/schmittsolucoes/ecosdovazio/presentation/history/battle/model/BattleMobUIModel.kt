package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob

data class BattleMobUIModel(
    val mobId: String,
    val phaseMobId: String,
    val name: String,
    val description: String,
    @DrawableRes val image: Int,
    val mobCategory: MobCategory,
    val offensiveMultiplier: Double = 1.0,
    val defensiveMultiplier: Double = 0.0,
    val totalHealth: Long,
    val actualHealth: Long,
    val healthProgress: Float,
    val level: Long,
    val attributes: Mob.Attributes,
    val skills: List<MobSkillUIModel> = emptyList(),
    val activeDots: List<ActiveDotUIModel.CharActiveDotUIModel> = emptyList()
)
