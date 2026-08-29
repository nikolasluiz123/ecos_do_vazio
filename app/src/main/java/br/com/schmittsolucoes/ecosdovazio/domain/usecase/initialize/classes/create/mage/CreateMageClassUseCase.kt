package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.MAGE_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.MAGE_CLASS_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_ARCANE_FOCUS_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_ARCANE_MISSILE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_ESSENCE_DRAIN_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_FLAMING_TOUCH_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_FLASH_FREEZE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_MANA_BARRIER
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_METEOR_SHOWER_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_SHOCKWAVE_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateClassResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateMageClassUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(): CreateClassResult {
        val mage = Class(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.MAGE_CLASS_NAME,
            descriptionTranslationId = TranslationIdentifier.MAGE_CLASS_DESCRIPTION,
            classCategory = ClassCategory.MAGE,
            images = Class.Images(
                battleImageName = BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY,
                presentationImageName = MAGE_CLASS_IMAGE_KEY,
                profileImageName = MAGE_CLASS_PROFILE_IMAGE_KEY
            ),
            attributes = Class.Attributes(
                incrementIntelligence = 10,
                incrementDexterity = 6,
                incrementVitality = 1,
                incrementPhysicalResistance = 1,
                incrementMagicResistance = 2
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.ARCANE_MISSILE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.ARCANE_MISSILE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = mage.id,
                damage = 30,
                refreshTime = 1,
                minLevel = 1,
                imageName = SKILL_ARCANE_MISSILE_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 10) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.QUICK_FREEZE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.QUICK_FREEZE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_DEBUFF,
                classId = mage.id,
                damage = 15,
                multiplier = 0.1,
                refreshTime = 3,
                duration = 3,
                minLevel = 2,
                imageName = SKILL_FLASH_FREEZE_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 11, requiredDexterity = 7) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.MANA_BARRIER_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.MANA_BARRIER_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_BUFF,
                classId = mage.id,
                multiplier = 0.1,
                duration = 3,
                refreshTime = 4,
                minLevel = 4,
                imageName = SKILL_MANA_BARRIER,
                attributes = Skill.Attributes(requiredIntelligence = 12, requiredMagicResistance = 5) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.FLAMING_TOUCH_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.FLAMING_TOUCH_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE_OVER_TIME,
                classId = mage.id,
                damage = 15,
                duration = 3,
                refreshTime = 4,
                minLevel = 5,
                imageName = SKILL_FLAMING_TOUCH_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 14, requiredDexterity = 7) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.SHOCK_WAVE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.SHOCK_WAVE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = mage.id,
                damage = 30,
                refreshTime = 4,
                minLevel = 8,
                imageName = SKILL_SHOCKWAVE_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 15, requiredDexterity = 9, requiredMagicResistance = 3) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.ARCANE_FOCUS_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.ARCANE_FOCUS_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                classId = mage.id,
                multiplier = 0.2,
                duration = 3,
                refreshTime = 4,
                minLevel = 10,
                imageName = SKILL_ARCANE_FOCUS_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 17, requiredDexterity = 11) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.DRAIN_ESSENCE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.DRAIN_ESSENCE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.VAMPIRIC_DAMAGE,
                classId = mage.id,
                damage = 40,
                multiplier = 0.5,
                refreshTime = 5,
                minLevel = 12,
                imageName = SKILL_ESSENCE_DRAIN_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 19, requiredMagicResistance = 7, requiredVitality = 4) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.METEOR_SHOWER_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.METEOR_SHOWER_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = mage.id,
                damage = 50,
                refreshTime = 8,
                minLevel = 15,
                imageName = SKILL_METEOR_SHOWER_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 25, requiredDexterity = 12, requiredMagicResistance = 8) 
            )
        )
        return CreateClassResult(mage, skills)
    }
}