package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface UsedCharSkillInfo {
    val skillCategory: SkillCategory
    val refreshTime: Int
    val damage: Long get() = 0

    data class CommonDamage(
        override val refreshTime: Int,
        override val damage: Long
    ): UsedCharSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class DamageOverTime(
        override val refreshTime: Int,
        override val damage: Long,
        val duration: Int,
    ): UsedCharSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class VampiricDamage(
        override val refreshTime: Int,
        override val damage: Long,
        val multiplier: Double,
    ): UsedCharSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.VAMPIRIC_DAMAGE
    }

    data class AreaDamage(
        override val refreshTime: Int,
        override val damage: Long
    ): UsedCharSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.AREA_DAMAGE
    }

    data class Buff(
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        val multiplier: Double,
        val duration: Int,
    ): UsedCharSkillInfo

    data class Debuff(
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val damage: Long = 0,
        val multiplier: Double,
        val duration: Int,
    ): UsedCharSkillInfo
}