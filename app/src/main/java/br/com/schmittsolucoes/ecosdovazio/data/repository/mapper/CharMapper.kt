package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSelectionTuple
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection

fun Char.toEntity(): CharEntity {
    return CharEntity(
        id = id,
        name = name,
        experience = experience,
        classId = classId,
        userId = userId,
        specializationId = specializationId,
        level = level,
        strength = strength,
        dexterity = dexterity,
        intelligence = intelligence,
        physicalResistance = physicalResistance,
        magicResistance = magicResistance,
        vitality = vitality,
        agility = agility
    )
}

fun CharSelectionTuple.toDomain(): CharSelection {
    return CharSelection(
        id = id,
        name = name,
        presentationImageName = presentationImageName
    )
}