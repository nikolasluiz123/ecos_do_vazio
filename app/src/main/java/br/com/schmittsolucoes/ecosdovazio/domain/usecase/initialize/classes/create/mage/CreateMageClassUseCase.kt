package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.MAGE_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.MAGE_CLASS_PROFILE_IMAGE_KEY
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
                damage = 20,
                refreshTime = 1,
                minLevel = 1
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.QUICK_FREEZE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.QUICK_FREEZE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_DEBUFF,
                classId = mage.id,
                damage = 15,
                multiplier = 2.0,
                refreshTime = 3,
                minLevel = 2
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.MANA_BARRIER_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.MANA_BARRIER_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_MAGIC_BUFF,
                classId = mage.id,
                multiplier = 0.4,
                duration = 2,
                refreshTime = 5,
                minLevel = 4
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.FLAMING_TOUCH_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.FLAMING_TOUCH_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE_OVER_TIME,
                classId = mage.id,
                damage = 12,
                multiplier = 3.0,
                refreshTime = 4,
                minLevel = 5
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.SHOCK_WAVE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.SHOCK_WAVE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = mage.id,
                damage = 25,
                refreshTime = 4,
                minLevel = 8
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.ARCANE_FOCUS_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.ARCANE_FOCUS_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                classId = mage.id,
                multiplier = 0.5,
                duration = 2,
                refreshTime = 5,
                minLevel = 10
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.DRAIN_ESSENCE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.DRAIN_ESSENCE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = mage.id,
                damage = 35,
                refreshTime = 5,
                minLevel = 12
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.METEOR_SHOWER_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.METEOR_SHOWER_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = mage.id,
                damage = 50,
                refreshTime = 8,
                minLevel = 15
            )
        )

        return CreateClassResult(mage, skills)
    }
}
