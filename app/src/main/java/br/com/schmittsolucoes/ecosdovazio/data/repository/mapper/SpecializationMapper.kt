package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.SpecializationEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization

fun Specialization.toEntity() = SpecializationEntity(
    id = id,
    nameTranslationId = nameTranslationId,
    descriptionTranslationId = descriptionTranslationId,
    classId = classId,
    battleImageName = images.battleImageName,
    presentationImageName = images.presentationImageName,
    profileImageName = images.profileImageName,
    incrementStrength = attributes.incrementStrength,
    incrementDexterity = attributes.incrementDexterity,
    incrementIntelligence = attributes.incrementIntelligence,
    incrementPhysicalResistance = attributes.incrementPhysicalResistance,
    incrementMagicResistance = attributes.incrementMagicResistance,
    incrementVitality = attributes.incrementVitality,
    incrementAgility = attributes.incrementAgility
)

fun SpecializationEntity.toDomain() = Specialization(
    id = id,
    nameTranslationId = nameTranslationId,
    descriptionTranslationId = descriptionTranslationId,
    classId = classId,
    images = Specialization.Images(
        battleImageName = battleImageName,
        presentationImageName = presentationImageName,
        profileImageName = profileImageName
    ),
    attributes = Specialization.Attributes(
        incrementStrength = incrementStrength,
        incrementDexterity = incrementDexterity,
        incrementIntelligence = incrementIntelligence,
        incrementPhysicalResistance = incrementPhysicalResistance,
        incrementMagicResistance = incrementMagicResistance,
        incrementVitality = incrementVitality,
        incrementAgility = incrementAgility
    )
)
