package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.BattleCharTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharAttributesTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharBaseDamageDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharCriticalDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharDodgeDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHeaderTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHealthDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharLevelInfoTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharMagicResistanceDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharPhysicalResistanceDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSelectionTuple
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharBaseDamageData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharCriticalData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharDodgeData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHeader
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHealthData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharMagicResistanceData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharPhysicalResistanceData
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier

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

fun CharEntity.toDomain(): Char {
    return Char(
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

fun CharHeaderTuple.toDomain(): CharHeader {
    return CharHeader(
        name = name,
        profileImageName = profileImageName
    )
}

fun CharHealthDataTuple.toDomain(): CharHealthData {
    return CharHealthData(
        classCategory = classCategory,
        vitality = CharAttribute(
            charValue = charVitality,
            classValue = classIncrementVitality,
            specializationValue = specializationIncrementVitality
        )
    )
}

fun CharBaseDamageDataTuple.toDomain(): CharBaseDamageData {
    return CharBaseDamageData(
        classCategory = classCategory,
        attributes = listOf(
            IdentifiedCharAttribute(
                id = AttributeIdentifier.STRENGTH,
                attribute = CharAttribute(
                    charValue = charStrength,
                    classValue = classIncrementStrength,
                    specializationValue = specializationIncrementStrength
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.DEXTERITY,
                attribute = CharAttribute(
                    charValue = charDexterity,
                    classValue = classIncrementDexterity,
                    specializationValue = specializationIncrementDexterity
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.INTELLIGENCE,
                attribute = CharAttribute(
                    charValue = charIntelligence,
                    classValue = classIncrementIntelligence,
                    specializationValue = specializationIncrementIntelligence
                )
            )
        )
    )
}

fun CharPhysicalResistanceDataTuple.toDomain(): CharPhysicalResistanceData {
    return CharPhysicalResistanceData(
        classCategory = classCategory,
        physicalResistance = CharAttribute(
            charValue = charPhysicalResistance,
            classValue = classIncrementPhysicalResistance,
            specializationValue = specializationIncrementPhysicalResistance
        )
    )
}

fun CharMagicResistanceDataTuple.toDomain(): CharMagicResistanceData {
    return CharMagicResistanceData(
        classCategory = classCategory,
        magicResistance = CharAttribute(
            charValue = charMagicResistance,
            classValue = classIncrementMagicResistance,
            specializationValue = specializationIncrementMagicResistance
        )
    )
}

fun CharCriticalDataTuple.toDomain(): CharCriticalData {
    return CharCriticalData(
        classCategory = classCategory,
        dexterity = CharAttribute(
            charValue = charDexterity,
            classValue = classIncrementDexterity,
            specializationValue = specializationIncrementDexterity
        )
    )
}

fun CharDodgeDataTuple.toDomain(): CharDodgeData {
    return CharDodgeData(
        classCategory = classCategory,
        agility = CharAttribute(
            charValue = charAgility,
            classValue = classIncrementAgility,
            specializationValue = specializationIncrementAgility
        )
    )
}

fun CharLevelInfoTuple.toDomain(nextLevelExperience: Long): CharLevelInfo {
    return CharLevelInfo(
        level = level,
        experience = experience,
        nextLevelExperience = nextLevelExperience
    )
}

fun CharAttributesTuple.toDomain(maxAttributeValue: Long): CharAttributes {
    return CharAttributes(
        maxAttributeValue = maxAttributeValue,
        attributes = listOf(
            IdentifiedCharAttribute(
                id = AttributeIdentifier.STRENGTH,
                attribute = CharAttribute(
                    charValue = charStrength,
                    classValue = classIncrementStrength,
                    specializationValue = specializationIncrementStrength ?: 0L
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.DEXTERITY,
                attribute = CharAttribute(
                    charValue = charDexterity,
                    classValue = classIncrementDexterity,
                    specializationValue = specializationIncrementDexterity ?: 0L
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.INTELLIGENCE,
                attribute = CharAttribute(
                    charValue = charIntelligence,
                    classValue = classIncrementIntelligence,
                    specializationValue = specializationIncrementIntelligence ?: 0L
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.PHYSICAL_RESISTANCE,
                attribute = CharAttribute(
                    charValue = charPhysicalResistance,
                    classValue = classIncrementPhysicalResistance,
                    specializationValue = specializationIncrementPhysicalResistance ?: 0L
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.MAGIC_RESISTANCE,
                attribute = CharAttribute(
                    charValue = charMagicResistance,
                    classValue = classIncrementMagicResistance,
                    specializationValue = specializationIncrementMagicResistance ?: 0L
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.VITALITY,
                attribute = CharAttribute(
                    charValue = charVitality,
                    classValue = classIncrementVitality,
                    specializationValue = specializationIncrementVitality ?: 0L
                )
            ),
            IdentifiedCharAttribute(
                id = AttributeIdentifier.AGILITY,
                attribute = CharAttribute(
                    charValue = charAgility,
                    classValue = classIncrementAgility,
                    specializationValue = specializationIncrementAgility ?: 0L
                )
            )
        )
    )
}

fun BattleCharTuple.toDomain(): BattleChar {
    return BattleChar(
        level = level,
        name = name,
        battleImageName = battleImageName,
        classCategory = classCategory,
        strength = CharAttribute(
            charValue = charStrength,
            classValue = classIncrementStrength,
            specializationValue = specializationIncrementStrength
        ),
        dexterity = CharAttribute(
            charValue = charDexterity,
            classValue = classIncrementDexterity,
            specializationValue = specializationIncrementDexterity
        ),
        intelligence = CharAttribute(
            charValue = charIntelligence,
            classValue = classIncrementIntelligence,
            specializationValue = specializationIncrementIntelligence
        ),
        physicalResistance = CharAttribute(
            charValue = charPhysicalResistance,
            classValue = classIncrementPhysicalResistance,
            specializationValue = specializationIncrementPhysicalResistance
        ),
        magicResistance = CharAttribute(
            charValue = charMagicResistance,
            classValue = classIncrementMagicResistance,
            specializationValue = specializationIncrementMagicResistance
        ),
        vitality = CharAttribute(
            charValue = charVitality,
            classValue = classIncrementVitality,
            specializationValue = specializationIncrementVitality
        ),
        agility = CharAttribute(
            charValue = charAgility,
            classValue = classIncrementAgility,
            specializationValue = specializationIncrementAgility
        )
    )
}
