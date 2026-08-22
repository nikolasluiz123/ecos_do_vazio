package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class BattleCharUIModel(
    val level: Long,
    val name: String,
    @DrawableRes val battleImage: Int,
    val classCategory: ClassCategory,
    val offensiveMultiplier: Double = 1.0,
    val defensiveMultiplier: Double = 0.0,
    val totalHealth: Long,
    val actualHealth: Long,
    val healthProgress: Float,
    val strength: CharAttribute,
    val dexterity: CharAttribute,
    val intelligence: CharAttribute,
    val physicalResistance: CharAttribute,
    val magicResistance: CharAttribute,
    val vitality: CharAttribute,
    val agility: CharAttribute,
    val damageSkills: List<CharSkillUIModel> = emptyList(),
    val buffSkills: List<CharSkillUIModel> = emptyList(),
    val debuffSkills: List<CharSkillUIModel> = emptyList(),
    val activeDots: List<ActiveDotUIModel.MobActiveDotUIModel> = emptyList(),
    val criticalFailCount: Int = 0
)
