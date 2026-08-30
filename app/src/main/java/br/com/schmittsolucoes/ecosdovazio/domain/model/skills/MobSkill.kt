package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface MobSkill {
    val id: String
    val name: String
    val description: String
    val imageName: String
    val skillCategory: SkillCategory
    val refreshTime: Int
    val currentRefreshTime: Int
    val blocked: Boolean
    val minLevel: Long

    data class CommonDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val imageName: String,
        override val refreshTime: Int,
        override val currentRefreshTime: Int = 0,
        override val blocked: Boolean = false,
        override val minLevel: Long,
        val damage: Long
    ) : MobSkill {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class DamageOverTime(
        override val id: String,
        override val name: String,
        override val description: String,
        override val imageName: String,
        override val refreshTime: Int,
        override val currentRefreshTime: Int = 0,
        override val blocked: Boolean = false,
        override val minLevel: Long,
        val damage: Long,
        val duration: Int
    ) : MobSkill {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class VampiricDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val imageName: String,
        override val refreshTime: Int,
        override val currentRefreshTime: Int = 0,
        override val blocked: Boolean = false,
        override val minLevel: Long,
        val damage: Long,
        val multiplier: Double
    ) : MobSkill {
        override val skillCategory: SkillCategory = SkillCategory.VAMPIRIC_DAMAGE
    }

    data class Buff(
        override val id: String,
        override val name: String,
        override val description: String,
        override val imageName: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val currentRefreshTime: Int = 0,
        override val blocked: Boolean = false,
        override val minLevel: Long,
        val multiplier: Double,
        val duration: Int
    ) : MobSkill

    data class Debuff(
        override val id: String,
        override val name: String,
        override val description: String,
        override val imageName: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val currentRefreshTime: Int = 0,
        override val blocked: Boolean = false,
        override val minLevel: Long,
        val multiplier: Double,
        val duration: Int
    ) : MobSkill

    data class Heal(
        override val id: String,
        override val name: String,
        override val description: String,
        override val imageName: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val currentRefreshTime: Int = 0,
        override val blocked: Boolean = false,
        override val minLevel: Long,
        val lifeRestore: Long
    ) : MobSkill
}
