package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_GUARDIAN_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.GUARDIAN_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.GUARDIAN_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_HOLY_SHIELD_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_COUNTER_ATTACK_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_BASTION_KEY
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
                minLevel = 15,
                imageName = SKILL_HOLY_SHIELD_KEY,
                attributes = Skill.Attributes(requiredStrength = 25)
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
                minLevel = 16,
                imageName = SKILL_COUNTER_ATTACK_KEY,
                attributes = Skill.Attributes(requiredStrength = 26)
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
                minLevel = 18,
                imageName = SKILL_BASTION_KEY,
                attributes = Skill.Attributes(requiredStrength = 28)
            )
        )

        return CreateSpecializationResult(guardian, skills)
    }
}