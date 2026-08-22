package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo

sealed interface ActiveDotUIModel {
    val skillId: String
    val skillName: String
    val skillDescription: String
    val remainingTurns: Int
    @get:DrawableRes val skillImage: Int

    data class CharActiveDotUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val skillInfo: UsedCharSkillInfo.DamageOverTime
    ) : ActiveDotUIModel

    data class MobActiveDotUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val sourceId: String,
        val skillInfo: UsedMobSkillInfo.DamageOverTime
    ) : ActiveDotUIModel
}
