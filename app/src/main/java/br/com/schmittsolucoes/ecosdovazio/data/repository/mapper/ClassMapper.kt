package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.ClassEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

fun Class.toEntity() = ClassEntity(
    id = id,
    nameTranslationId = nameTranslationId.name,
    descriptionTranslationId = descriptionTranslationId.name,
    classCategory = classCategory,
    battleImageName = battleImageName,
    presentationImageName = presentationImageName,
    incrementStrength = incrementStrength,
    incrementDexterity = incrementDexterity,
    incrementIntelligence = incrementIntelligence,
    incrementPhysicalResistance = incrementPhysicalResistance,
    incrementMagicResistance = incrementMagicResistance,
    incrementVitality = incrementVitality,
    incrementAgility = incrementAgility
)

fun ClassEntity.toDomain() = Class(
    id = id,
    nameTranslationId = TranslationIdentifier.valueOf(nameTranslationId),
    descriptionTranslationId = TranslationIdentifier.valueOf(descriptionTranslationId),
    classCategory = classCategory,
    battleImageName = battleImageName,
    presentationImageName = presentationImageName,
    incrementStrength = incrementStrength,
    incrementDexterity = incrementDexterity,
    incrementIntelligence = incrementIntelligence,
    incrementPhysicalResistance = incrementPhysicalResistance,
    incrementMagicResistance = incrementMagicResistance,
    incrementVitality = incrementVitality,
    incrementAgility = incrementAgility
)
