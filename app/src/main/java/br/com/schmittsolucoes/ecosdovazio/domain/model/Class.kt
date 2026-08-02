package br.com.schmittsolucoes.ecosdovazio.domain.model

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

data class Class(
    val id: String,
    val nameTranslationId: TranslationIdentifier,
    val descriptionTranslationId: TranslationIdentifier,
    val classCategory: ClassCategory,
    val battleImageName: String,
    val presentationImageName: String,
    val incrementStrength: Long = 0,
    val incrementDexterity: Long = 0,
    val incrementIntelligence: Long = 0,
    val incrementPhysicalResistance: Long = 0,
    val incrementMagicResistance: Long = 0,
    val incrementVitality: Long = 0,
    val incrementAgility: Long = 0,
)
