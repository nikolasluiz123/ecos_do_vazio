package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer

import br.com.schmittsolucoes.ecosdovazio.data.provider.ARCHER_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.ARCHER_CLASS_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateClassResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateArcherClassUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(): CreateClassResult {
        val archer = Class(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.ARCHER_CLASS_NAME,
            descriptionTranslationId = TranslationIdentifier.ARCHER_CLASS_DESCRIPTION,
            classCategory = ClassCategory.ARCHER,
            images = Class.Images(
                battleImageName = BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY,
                presentationImageName = ARCHER_CLASS_IMAGE_KEY,
                profileImageName = ARCHER_CLASS_PROFILE_IMAGE_KEY
            ),
            attributes = Class.Attributes(
                incrementDexterity = 10,
                incrementAgility = 5,
                incrementVitality = 1,
                incrementPhysicalResistance = 2,
                incrementMagicResistance = 2
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.PRECISION_SHOT_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.PRECISION_SHOT_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = archer.id,
                damage = 22,
                refreshTime = 1,
                minLevel = 1
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.RAPID_FIRE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.RAPID_FIRE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = archer.id,
                damage = 12,
                refreshTime = 3,
                minLevel = 2
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.TACTICAL_RETREAT_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.TACTICAL_RETREAT_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_PHYSICAL_BUFF,
                classId = archer.id,
                multiplier = 0.3,
                duration = 2,
                refreshTime = 4,
                minLevel = 4
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.THORN_TRAP_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.THORN_TRAP_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = archer.id,
                damage = 18,
                multiplier = 1.0,
                refreshTime = 5,
                minLevel = 5
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.POISON_ARROW_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.POISON_ARROW_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE_OVER_TIME,
                classId = archer.id,
                damage = 10,
                multiplier = 2.0,
                refreshTime = 3,
                minLevel = 8
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.PERFECT_AIM_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.PERFECT_AIM_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                classId = archer.id,
                multiplier = 0.6,
                duration = 2,
                refreshTime = 5,
                minLevel = 10
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.SMOKE_SCREEN_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.SMOKE_SCREEN_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_PHYSICAL_BUFF,
                classId = archer.id,
                multiplier = 0.2,
                duration = 3,
                refreshTime = 6,
                minLevel = 12
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.PIERCING_SHOT_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.PIERCING_SHOT_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                classId = archer.id,
                damage = 40,
                refreshTime = 5,
                minLevel = 15
            )
        )

        return CreateClassResult(archer, skills)
    }
}
