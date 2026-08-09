package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.MobEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob

fun Mob.toEntity() = MobEntity(
    id = id,
    nameTranslationId = nameTranslationId.name,
    descriptionTranslationId = descriptionTranslationId.name,
    strength = attributes.strength,
    dexterity = attributes.dexterity,
    intelligence = attributes.intelligence,
    physicalResistance = attributes.physicalResistance,
    magicResistance = attributes.magicResistance,
    vitality = attributes.vitality,
    agility = attributes.agility,
    imageName = imageName,
    mobCategory = mobCategory
)

fun MobEntity.toDomain() = Mob(
    id = id,
    nameTranslationId = TranslationIdentifier.valueOf(nameTranslationId),
    descriptionTranslationId = TranslationIdentifier.valueOf(descriptionTranslationId),
    imageName = imageName,
    mobCategory = mobCategory,
    attributes = Mob.Attributes(
        strength = strength,
        dexterity = dexterity,
        intelligence = intelligence,
        physicalResistance = physicalResistance,
        magicResistance = magicResistance,
        vitality = vitality,
        agility = agility
    )
)
