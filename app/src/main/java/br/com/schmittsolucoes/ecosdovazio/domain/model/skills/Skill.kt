package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

data class Skill(
    val id: String,
    val nameTranslationId: TranslationIdentifier,
    val descriptionTranslationId: TranslationIdentifier,
    val skillCategory: SkillCategory,
    val classId: String? = null,
    val specializationId: String? = null,
    val mobId: String? = null,
    val damage: Long? = null,
    val multiplier: Double? = null,
    val duration: Int? = null,
    val refreshTime: Int,
    val minLevel: Long,
    val imageName: String,
    val attributes: Attributes
) {
    data class Attributes(
        val requiredStrength: Long = 0,
        val requiredDexterity: Long = 0,
        val requiredIntelligence: Long = 0,
        val requiredPhysicalResistance: Long = 0,
        val requiredMagicResistance: Long = 0,
        val requiredVitality: Long = 0,
        val requiredAgility: Long = 0,
    )
}
