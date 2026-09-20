package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

data class CharSkillTuple(
    val id: String,
    val name: String,
    val translationIdentifier: TranslationIdentifier,
    val description: String,
    val skillCategory: SkillCategory,
    val damage: Long?,
    val multiplier: Double?,
    val duration: Int?,
    val lifeRestore: Long?,
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
