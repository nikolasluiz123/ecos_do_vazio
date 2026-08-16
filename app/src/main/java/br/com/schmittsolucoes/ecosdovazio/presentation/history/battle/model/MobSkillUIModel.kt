package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface MobSkillUIModel {
    val id: String
    val skillCategory: SkillCategory
    val refreshTime: Int
    val currentRefreshTime: Int
    val blocked: Boolean

    data class CommonDamage(
        override val id: String,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val damage: Long
    ): MobSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class DamageOverTime(
        override val id: String,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val damage: Long,
        val duration: Int,
    ): MobSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class Buff(
        override val id: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val multiplier: Double,
        val duration: Int,
    ): MobSkillUIModel

    data class Debuff(
        override val id: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val multiplier: Double,
        val duration: Int,
    ): MobSkillUIModel
}
