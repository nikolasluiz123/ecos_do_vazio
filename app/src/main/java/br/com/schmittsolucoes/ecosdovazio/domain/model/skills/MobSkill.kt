package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface MobSkill {
    val id: String
    val skillCategory: SkillCategory
    val refreshTime: Int
    val minLevel: Long

    data class CommonDamage(
        override val id: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        val damage: Long
    ) : MobSkill {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class DamageOverTime(
        override val id: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        val damage: Long,
        val duration: Int
    ) : MobSkill {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class Buff(
        override val id: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val minLevel: Long,
        val multiplier: Double,
        val duration: Int
    ) : MobSkill

    data class Debuff(
        override val id: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val minLevel: Long,
        val multiplier: Double,
        val duration: Int
    ) : MobSkill
}
