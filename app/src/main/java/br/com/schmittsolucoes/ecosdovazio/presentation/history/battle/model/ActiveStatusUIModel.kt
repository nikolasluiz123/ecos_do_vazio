package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo

interface ActiveStatusUIModel {
    val skillId: String
    val skillName: String
    val skillDescription: String
    val remainingTurns: Int
    @get:DrawableRes val skillImage: Int
}

sealed interface CharActiveStatusUIModel: ActiveStatusUIModel {
    data class DoTUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val skillInfo: UsedCharSkillInfo.DamageOverTime
    ) : CharActiveStatusUIModel

    data class DebuffUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val skillCategory: SkillCategory,
        val skillInfo: UsedCharSkillInfo.Debuff
    ) : CharActiveStatusUIModel

    data class BuffUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val skillCategory: SkillCategory,
        val skillInfo: UsedCharSkillInfo.Buff
    ) : CharActiveStatusUIModel
}

sealed interface MobActiveStatusUIModel: ActiveStatusUIModel {
    data class DoTUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val sourceId: String,
        val skillInfo: UsedMobSkillInfo.DamageOverTime
    ) : MobActiveStatusUIModel

    data class DebuffUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val sourceId: String,
        val skillCategory: SkillCategory,
        val skillInfo: UsedMobSkillInfo.Debuff
    ) : MobActiveStatusUIModel

    data class BuffUIModel(
        override val skillId: String,
        override val skillName: String,
        override val skillDescription: String,
        override val remainingTurns: Int,
        @DrawableRes override val skillImage: Int,
        val sourceId: String,
        val skillCategory: SkillCategory,
        val skillInfo: UsedMobSkillInfo.Buff
    ) : MobActiveStatusUIModel
}
