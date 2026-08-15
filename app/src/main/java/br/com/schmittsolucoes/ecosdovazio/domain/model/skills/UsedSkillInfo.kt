package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface UsedSkillInfo {
    val skillCategory: SkillCategory
    val refreshTime: Int

    data class CommonDamage(
        override val refreshTime: Int,
        val damage: Long
    ): UsedSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class DamageOverTime(
        override val refreshTime: Int,
        val damage: Long,
        val duration: Int,
    ): UsedSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class Buff(
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        val multiplier: Double,
        val duration: Int,
    ): UsedSkillInfo

    data class Debuff(
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        val multiplier: Double,
        val duration: Int,
    ): UsedSkillInfo
}