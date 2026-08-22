package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.WARRIOR_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.WARRIOR_CLASS_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_HEAVY_STRIKE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_TACTICAL_ADVANCE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_DEFENSIVE_STANCE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_BLOOD_STRIKE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_BATTLE_RAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_RELENTLESS_CHARGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_BRUTAL_RIFT_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_ARMOR_BREAK_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateClassResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateWarriorClassUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(): CreateClassResult {
        val warrior = Class(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.WARRIOR_CLASS_NAME,
            descriptionTranslationId = TranslationIdentifier.WARRIOR_CLASS_DESCRIPTION,
            classCategory = ClassCategory.WARRIOR,
            images = Class.Images(
                battleImageName = BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY,
                presentationImageName = WARRIOR_CLASS_IMAGE_KEY,
                profileImageName = WARRIOR_CLASS_PROFILE_IMAGE_KEY
            ),
            attributes = Class.Attributes(
                incrementStrength = 10,
                incrementDexterity = 4,
                incrementVitality = 2,
                incrementPhysicalResistance = 3,
                incrementMagicResistance = 1
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.HEAVY_STRIKE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.HEAVY_STRIKE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = warrior.id,
                damage = 18,
                refreshTime = 1,
                minLevel = 1,
                imageName = SKILL_HEAVY_STRIKE_KEY,
                attributes = Skill.Attributes(requiredStrength = 10)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.TACTICAL_ADVANCE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.TACTICAL_ADVANCE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = warrior.id,
                damage = 15,
                refreshTime = 3,
                minLevel = 2,
                imageName = SKILL_TACTICAL_ADVANCE_KEY,
                attributes = Skill.Attributes(requiredStrength = 11, requiredDexterity = 5)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.DEFENSIVE_STANCE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.DEFENSIVE_STANCE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_PHYSICAL_BUFF,
                classId = warrior.id,
                multiplier = 2.0,
                refreshTime = 5,
                minLevel = 4,
                imageName = SKILL_DEFENSIVE_STANCE_KEY,
                attributes = Skill.Attributes(requiredStrength = 12, requiredPhysicalResistance = 5)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BLOODY_STRIKE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BLOODY_STRIKE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE_OVER_TIME,
                classId = warrior.id,
                damage = 10,
                duration = 3,
                refreshTime = 4,
                minLevel = 5,
                imageName = SKILL_BLOOD_STRIKE_KEY,
                attributes = Skill.Attributes(requiredStrength = 13, requiredDexterity = 6)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BATTLE_FURY_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BATTLE_FURY_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                classId = warrior.id,
                multiplier = 0.4,
                duration = 3,
                refreshTime = 4,
                minLevel = 8,
                imageName = SKILL_BATTLE_RAGE_KEY,
                attributes = Skill.Attributes(requiredStrength = 14, requiredVitality = 6)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.RELENTLESS_CHARGE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.RELENTLESS_CHARGE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = warrior.id,
                damage = 30,
                multiplier = 1.0,
                refreshTime = 5,
                minLevel = 10,
                imageName = SKILL_RELENTLESS_CHARGE_KEY,
                attributes = Skill.Attributes(requiredStrength = 16, requiredPhysicalResistance = 7, requiredDexterity = 7)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BRUTAL_RIFT_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BRUTAL_RIFT_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = warrior.id,
                damage = 25,
                refreshTime = 4,
                minLevel = 12,
                imageName = SKILL_BRUTAL_RIFT_KEY,
                attributes = Skill.Attributes(requiredStrength = 20, requiredVitality = 7)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BREAK_ARMOR_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BREAK_ARMOR_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_DEBUFF,
                classId = warrior.id,
                damage = 20,
                multiplier = 3.0,
                refreshTime = 6,
                minLevel = 15,
                imageName = SKILL_ARMOR_BREAK_KEY,
                attributes = Skill.Attributes(requiredStrength = 24, requiredDexterity = 10, requiredPhysicalResistance = 8)
            )
        )
        return CreateClassResult(warrior, skills)
    }
}