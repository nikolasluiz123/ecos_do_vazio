package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_BEASTMASTER_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BEASTMASTER_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BEASTMASTER_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_GROUND_TRAP_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_PRECISE_SHOT_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateSpecializationResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateBeastMasterSpecializationUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(classId: String): CreateSpecializationResult {
        val beastMaster = Specialization(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.BEAST_MASTER_SPEC_NAME.name,
            descriptionTranslationId = TranslationIdentifier.BEAST_MASTER_SPEC_DESCRIPTION.name,
            classId = classId,
            images = Specialization.Images(
                battleImageName = BATTLE_IMAGE_BEASTMASTER_SPECIALIZATION_IMAGE_KEY,
                presentationImageName = BEASTMASTER_SPECIALIZATION_IMAGE_KEY,
                profileImageName = BEASTMASTER_SPECIALIZATION_PROFILE_IMAGE_KEY
            ),
            attributes = Specialization.Attributes(
                incrementDexterity = 4,
                incrementPhysicalResistance = 2,
                incrementMagicResistance = 2,
                incrementAgility = 2
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.PRECISION_FIRE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.PRECISION_FIRE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = beastMaster.id,
                damage = 40,
                refreshTime = 3,
                minLevel = 15,
                imageName = SKILL_PRECISE_SHOT_KEY,
                attributes = Skill.Attributes(requiredDexterity = 20, requiredAgility = 10)
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.GROUND_TRAP_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.GROUND_TRAP_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = beastMaster.id,
                damage = 10,
                multiplier = 2.0,
                refreshTime = 6,
                minLevel = 18,
                imageName = SKILL_GROUND_TRAP_KEY,
                attributes = Skill.Attributes(requiredDexterity = 22, requiredAgility = 10)
            )
        )

        return CreateSpecializationResult(beastMaster, skills)
    }
}