package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

sealed interface CharSkill {
    val id: String
    val name: String
    val description: String
    val skillCategory: SkillCategory
    val refreshTime: Int
    val minLevel: Long
    val imageName: String
    val attributes: Attributes

    data class Attributes(
        val requiredStrength: Long,
        val requiredDexterity: Long,
        val requiredIntelligence: Long,
        val requiredPhysicalResistance: Long,
        val requiredMagicResistance: Long,
        val requiredVitality: Long,
        val requiredAgility: Long,
    )

    data class CommonDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        override val imageName: String,
        override val attributes: Attributes,
        val damage: Long
    ) : CharSkill {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE
    }

    data class AreaDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        override val imageName: String,
        override val attributes: Attributes,
        val damage: Long
    ) : CharSkill {
        override val skillCategory: SkillCategory = SkillCategory.AREA_DAMAGE
    }

    data class DamageOverTime(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        override val imageName: String,
        override val attributes: Attributes,
        val damage: Long,
        val duration: Int
    ) : CharSkill {
        override val skillCategory: SkillCategory = SkillCategory.DAMAGE_OVER_TIME
    }

    data class VampiricDamage(
        override val id: String,
        override val name: String,
        override val description: String,
        override val refreshTime: Int,
        override val minLevel: Long,
        override val imageName: String,
        override val attributes: Attributes,
        val damage: Long,
        val multiplier: Double
    ) : CharSkill {
        override val skillCategory: SkillCategory = SkillCategory.VAMPIRIC_DAMAGE
    }

    data class Buff(
        override val id: String,
        override val name: String,
        override val description: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val minLevel: Long,
        override val imageName: String,
        override val attributes: Attributes,
        val multiplier: Double,
        val duration: Int
    ) : CharSkill

    data class Debuff(
        override val id: String,
        override val name: String,
        override val description: String,
        override val skillCategory: SkillCategory,
        override val refreshTime: Int,
        override val minLevel: Long,
        override val imageName: String,
        override val attributes: Attributes,
        val multiplier: Double,
        val duration: Int,
        val damage: Long? = null
    ) : CharSkill
}
