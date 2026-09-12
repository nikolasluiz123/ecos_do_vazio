package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_GLADIATOR_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.GLADIATOR_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.GLADIATOR_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_WHIRLWIND_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateSpecializationResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateGladiatorSpecializationUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(classId: String): CreateSpecializationResult {
        val gladiator = Specialization(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.GLADIATOR_SPEC_NAME.name,
            descriptionTranslationId = TranslationIdentifier.GLADIATOR_SPEC_DESCRIPTION.name,
            classId = classId,
            images = Specialization.Images(
                battleImageName = BATTLE_IMAGE_GLADIATOR_SPECIALIZATION_IMAGE_KEY,
                presentationImageName = GLADIATOR_SPECIALIZATION_IMAGE_KEY,
                profileImageName = GLADIATOR_SPECIALIZATION_PROFILE_IMAGE_KEY
            ),
            attributes = Specialization.Attributes(
                incrementStrength = 7,
                incrementVitality = 3
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.WHIRLWIND_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.WHIRLWIND_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.AREA_DAMAGE,
                specializationId = gladiator.id,
                damage = 35,
                refreshTime = 4,
                minLevel = 18,
                imageName = SKILL_WHIRLWIND_KEY,
                attributes = Skill.Attributes(requiredStrength = 22, requiredDexterity = 10)
            )
        )

        return CreateSpecializationResult(gladiator, skills)
    }
}