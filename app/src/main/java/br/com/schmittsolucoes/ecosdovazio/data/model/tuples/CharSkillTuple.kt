package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

data class CharSkillTuple(
    val id: String,
    val name: String,
    val description: String,
    val skillCategory: SkillCategory,
    val damage: Long?,
    val multiplier: Double?,
    val duration: Int?,
    val refreshTime: Int,
    val minLevel: Long,
    val requiredStrength: Long,
    val requiredDexterity: Long,
    val requiredIntelligence: Long,
    val requiredPhysicalResistance: Long,
    val requiredMagicResistance: Long,
    val requiredVitality: Long,
    val requiredAgility: Long,
    val imageName: String,
)
