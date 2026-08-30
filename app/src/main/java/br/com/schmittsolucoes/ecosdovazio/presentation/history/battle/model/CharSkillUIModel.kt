package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill

sealed interface CharSkillUIModel {
    val id: String
    val name: String
    val description: String
    val skillCategory: SkillCategory
    val refreshTime: Int
    val minLevel: Long
    val image: Int
    val attributes: CharSkill.Attributes

    val currentRefreshTime: Int
    val blocked: Boolean

    data class CommonDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        @DrawableRes override val image: Int,
        override val attributes: CharSkill.Attributes,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val damage: Long
    ): CharSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class AreaDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        @DrawableRes override val image: Int,
        override val attributes: CharSkill.Attributes,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val damage: Long
    ): CharSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.AREA_DAMAGE
    }

    data class DamageOverTime(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        @DrawableRes override val image: Int,
        override val attributes: CharSkill.Attributes,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val damage: Long,
        val duration: Int,
    ): CharSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class VampiricDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        @DrawableRes override val image: Int,
        override val attributes: CharSkill.Attributes,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val damage: Long,
        val multiplier: Double,
    ): CharSkillUIModel {
        override val skillCategory: SkillCategory = SkillCategory.VAMPIRIC_DAMAGE
    }

    data class Buff(
        override val id: String,
        override val name: String,
        override val description: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val minLevel: Long,
        @DrawableRes override val image: Int,
        override val attributes: CharSkill.Attributes,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val multiplier: Double,
        val duration: Int,
    ): CharSkillUIModel

    data class Debuff(
        override val id: String,
        override val name: String,
        override val description: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val minLevel: Long,
        @DrawableRes override val image: Int,
        override val attributes: CharSkill.Attributes,
        override val currentRefreshTime: Int,
        override val blocked: Boolean,
        val multiplier: Double,
        val duration: Int,
        val damage: Long? = null
    ): CharSkillUIModel
}
