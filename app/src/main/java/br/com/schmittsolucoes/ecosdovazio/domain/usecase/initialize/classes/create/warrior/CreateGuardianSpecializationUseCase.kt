package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_GUARDIAN_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.GUARDIAN_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.GUARDIAN_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateSpecializationResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateGuardianSpecializationUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(classId: String): CreateSpecializationResult {
        val guardian = Specialization(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.GUARDIAN_SPEC_NAME.name,
            descriptionTranslationId = TranslationIdentifier.GUARDIAN_SPEC_DESCRIPTION.name,
            classId = classId,
            images = Specialization.Images(
                battleImageName = BATTLE_IMAGE_GUARDIAN_SPECIALIZATION_IMAGE_KEY,
                presentationImageName = GUARDIAN_SPECIALIZATION_IMAGE_KEY,
                profileImageName = GUARDIAN_SPECIALIZATION_PROFILE_IMAGE_KEY
            ),
            attributes = Specialization.Attributes(
                incrementPhysicalResistance = 4,
                incrementVitality = 4,
                incrementMagicResistance = 2
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.HOLY_SHIELD_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.HOLY_SHIELD_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_MAGIC_BUFF,
                specializationId = guardian.id,
                multiplier = 1.5,
                duration = 3,
                refreshTime = 5,
                minLevel = 15
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.COUNTERATTACK_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.COUNTERATTACK_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = guardian.id,
                damage = 20,
                multiplier = 2.0,
                refreshTime = 4,
                minLevel = 16
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BASTION_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BASTION_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_PHYSICAL_BUFF,
                specializationId = guardian.id,
                multiplier = 1.2,
                duration = 4,
                refreshTime = 6,
                minLevel = 18
            )
        )

        return CreateSpecializationResult(guardian, skills)
    }
}
