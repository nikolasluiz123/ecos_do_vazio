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
        charVitality = charVitality,
        classIncrementVitality = classIncrementVitality,
        specializationIncrementVitality = specializationIncrementVitality
    )
}

fun CharBaseDamageDataTuple.toDomain(): CharBaseDamageData {
    return CharBaseDamageData(
        classCategory = classCategory,
        strength = CharBaseDamageData.DamageAttributes(
            charValue = charStrength,
            classValue = classIncrementStrength,
            specializationValue = specializationIncrementStrength
        ),
        dexterity = CharBaseDamageData.DamageAttributes(
            charValue = charDexterity,
            classValue = classIncrementDexterity,
            specializationValue = specializationIncrementDexterity
        ),
        intelligence = CharBaseDamageData.DamageAttributes(
            charValue = charIntelligence,
            classValue = classIncrementIntelligence,
            specializationValue = specializationIncrementIntelligence
        )
    )
}

fun CharPhysicalResistanceDataTuple.toDomain(): CharPhysicalResistanceData {
    return CharPhysicalResistanceData(
        classCategory = classCategory,
        charPhysicalResistance = charPhysicalResistance,
        classIncrementPhysicalResistance = classIncrementPhysicalResistance,
        specializationIncrementPhysicalResistance = specializationIncrementPhysicalResistance
    )
}

fun CharMagicResistanceDataTuple.toDomain(): CharMagicResistanceData {
    return CharMagicResistanceData(
        classCategory = classCategory,
        charMagicResistance = charMagicResistance,
        classIncrementMagicResistance = classIncrementMagicResistance,
        specializationIncrementMagicResistance = specializationIncrementMagicResistance
    )
}

fun CharCriticalDataTuple.toDomain(): CharCriticalData {
    return CharCriticalData(
        classCategory = classCategory,
        charDexterity = charDexterity,
        classIncrementDexterity = classIncrementDexterity,
        specializationIncrementDexterity = specializationIncrementDexterity
    )
}

fun CharDodgeDataTuple.toDomain(): CharDodgeData {
    return CharDodgeData(
        classCategory = classCategory,
        charAgility = charAgility,
        classIncrementAgility = classIncrementAgility,
        specializationIncrementAgility = specializationIncrementAgility
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
            CharAttributes.Attributes(
                id = AttributeIdentifier.STRENGTH,
                charValue = charStrength,
                classValue = classIncrementStrength,
                specializationValue = specializationIncrementStrength ?: 0L
            ),
            CharAttributes.Attributes(
                id = AttributeIdentifier.DEXTERITY,
                charValue = charDexterity,
                classValue = classIncrementDexterity,
                specializationValue = specializationIncrementDexterity ?: 0L
            ),
            CharAttributes.Attributes(
                id = AttributeIdentifier.INTELLIGENCE,
                charValue = charIntelligence,
                classValue = classIncrementIntelligence,
                specializationValue = specializationIncrementIntelligence ?: 0L
            ),
            CharAttributes.Attributes(
                id = AttributeIdentifier.PHYSICAL_RESISTANCE,
                charValue = charPhysicalResistance,
                classValue = classIncrementPhysicalResistance,
                specializationValue = specializationIncrementPhysicalResistance ?: 0L
            ),
            CharAttributes.Attributes(
                id = AttributeIdentifier.MAGIC_RESISTANCE,
                charValue = charMagicResistance,
                classValue = classIncrementMagicResistance,
                specializationValue = specializationIncrementMagicResistance ?: 0L
            ),
            CharAttributes.Attributes(
                id = AttributeIdentifier.VITALITY,
                charValue = charVitality,
                classValue = classIncrementVitality,
                specializationValue = specializationIncrementVitality ?: 0L
            ),
            CharAttributes.Attributes(
                id = AttributeIdentifier.AGILITY,
                charValue = charAgility,
                classValue = classIncrementAgility,
                specializationValue = specializationIncrementAgility ?: 0L
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
        strength = BattleChar.Attribute(
            charValue = charStrength,
            classValue = classIncrementStrength,
            specializationValue = specializationIncrementStrength
        ),
        dexterity = BattleChar.Attribute(
            charValue = charDexterity,
            classValue = classIncrementDexterity,
            specializationValue = specializationIncrementDexterity
        ),
        intelligence = BattleChar.Attribute(
            charValue = charIntelligence,
            classValue = classIncrementIntelligence,
            specializationValue = specializationIncrementIntelligence
        ),
        physicalResistance = BattleChar.Attribute(
            charValue = charPhysicalResistance,
            classValue = classIncrementPhysicalResistance,
            specializationValue = specializationIncrementPhysicalResistance
        ),
        magicResistance = BattleChar.Attribute(
            charValue = charMagicResistance,
            classValue = classIncrementMagicResistance,
            specializationValue = specializationIncrementMagicResistance
        ),
        vitality = BattleChar.Attribute(
            charValue = charVitality,
            classValue = classIncrementVitality,
            specializationValue = specializationIncrementVitality
        ),
        agility = BattleChar.Attribute(
            charValue = charAgility,
            classValue = classIncrementAgility,
            specializationValue = specializationIncrementAgility
        )
    )
}
