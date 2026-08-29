package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_WATER_MAGE_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.WATER_MAGE_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.WATER_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_ICE_LANCE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_INVIGORATING_HEAL_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_BLIZZARD_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateSpecializationResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateWaterMageSpecializationUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(classId: String): CreateSpecializationResult {
        val waterMage = Specialization(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.WATER_ELEMENTAL_SPEC_NAME.name,
            descriptionTranslationId = TranslationIdentifier.WATER_ELEMENTAL_SPEC_DESCRIPTION.name,
            classId = classId,
            images = Specialization.Images(
                battleImageName = BATTLE_IMAGE_WATER_MAGE_SPECIALIZATION_IMAGE_KEY,
                presentationImageName = WATER_MAGE_SPECIALIZATION_IMAGE_KEY,
                profileImageName = WATER_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY
            ),
            attributes = Specialization.Attributes(
                incrementIntelligence = 5,
                incrementDexterity = 5
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.ICE_SPEAR_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.ICE_SPEAR_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = waterMage.id,
                damage = 35,
                duration = 2,
                refreshTime = 3,
                minLevel = 15,
                imageName = SKILL_ICE_LANCE_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 25) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.INVIGORATING_HEAL_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.INVIGORATING_HEAL_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_BUFF,
                specializationId = waterMage.id,
                damage = 20,
                refreshTime = 4,
                minLevel = 16,
                imageName = SKILL_INVIGORATING_HEAL_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 26) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BLIZZARD_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BLIZZARD_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = waterMage.id,
                damage = 25,
                multiplier = 2.0,
                refreshTime = 6,
                minLevel = 18,
                imageName = SKILL_BLIZZARD_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 28) 
            )
        )

        return CreateSpecializationResult(waterMage, skills)
    }
}