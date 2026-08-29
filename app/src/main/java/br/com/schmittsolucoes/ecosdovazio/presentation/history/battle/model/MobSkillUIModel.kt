package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface MobSkillUIModel {
    val id: String
    val name: String
    val description: String
    val image: Int
    val skillCategory: SkillCategory
    val refreshTime: Int
    val currentRefreshTime: Int
    val blocked: Boolean
    val minLevel: Long

    data class CommonDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        @DrawableRes override val image: Int,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        override val minLevel: Long,
        val damage: Long
    ): MobSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class DamageOverTime(
        override val id: String,
        override val name: String,
        override val description: String,
        @DrawableRes override val image: Int,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        override val minLevel: Long,
        val damage: Long,
        val duration: Int,
    ): MobSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class VampiricDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        @DrawableRes override val image: Int,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        override val minLevel: Long,
        val damage: Long,
        val multiplier: Double,
    ): MobSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.VAMPIRIC_DAMAGE
    }

    data class Buff(
        override val id: String,
        override val name: String,
        override val description: String,
        @DrawableRes override val image: Int,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        override val minLevel: Long,
        val multiplier: Double,
        val duration: Int,
    ): MobSkillUIModel

    data class Debuff(
        override val id: String,
        override val name: String,
        override val description: String,
        @DrawableRes override val image: Int,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        override val minLevel: Long,
        val multiplier: Double,
        val duration: Int,
    ): MobSkillUIModel
}
