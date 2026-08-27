package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface UsedMobSkillInfo {
    val skillId: String
    val skillCategory: SkillCategory
    val refreshTime: Int
    val damage: Long get() = 0

    data class CommonDamage(
        override val refreshTime: Int,
        override val damage: Long,
        override val skillId: String
    ): UsedMobSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class DamageOverTime(
        override val refreshTime: Int,
        override val damage: Long,
        override val skillId: String,
        val duration: Int,
    ): UsedMobSkillInfo {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class Buff(
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val skillId: String,
        val multiplier: Double,
        val duration: Int,
    ): UsedMobSkillInfo

    data class Debuff(
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val skillId: String,
        val multiplier: Double,
        val duration: Int,
    ): UsedMobSkillInfo
}
