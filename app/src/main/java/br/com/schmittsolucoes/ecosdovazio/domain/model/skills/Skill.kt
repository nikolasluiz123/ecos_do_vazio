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
    val imageName: String? = null,
)
