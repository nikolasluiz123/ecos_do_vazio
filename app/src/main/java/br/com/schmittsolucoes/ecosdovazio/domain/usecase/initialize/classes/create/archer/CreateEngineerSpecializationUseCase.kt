package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_ENGINEER_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.ENGINEER_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.ENGINEER_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateSpecializationResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateEngineerSpecializationUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(classId: String): CreateSpecializationResult {
        val engineer = Specialization(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.ENGINEER_SPEC_NAME.name,
            descriptionTranslationId = TranslationIdentifier.ENGINEER_SPEC_DESCRIPTION.name,
            classId = classId,
            images = Specialization.Images(
                battleImageName = BATTLE_IMAGE_ENGINEER_SPECIALIZATION_IMAGE_KEY,
                presentationImageName = ENGINEER_SPECIALIZATION_IMAGE_KEY,
                profileImageName = ENGINEER_SPECIALIZATION_PROFILE_IMAGE_KEY
            ),
            attributes = Specialization.Attributes(
                incrementDexterity = 5,
                incrementAgility = 5
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.CANNON_SHOT_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.CANNON_SHOT_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = engineer.id,
                damage = 50,
                refreshTime = 4,
                minLevel = 15
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.AUTO_TURRET_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.AUTO_TURRET_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE_OVER_TIME,
                specializationId = engineer.id,
                damage = 15,
                multiplier = 3.0,
                refreshTime = 5,
                minLevel = 16
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.FRAG_GRENADE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.FRAG_GRENADE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = engineer.id,
                damage = 30,
                refreshTime = 5,
                minLevel = 18
            )
        )

        return CreateSpecializationResult(engineer, skills)
    }
}
