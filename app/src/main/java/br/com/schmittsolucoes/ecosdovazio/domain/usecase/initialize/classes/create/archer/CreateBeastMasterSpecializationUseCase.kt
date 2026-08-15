package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_BEASTMASTER_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BEASTMASTER_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BEASTMASTER_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_PRECISE_SHOT_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_EAGLE_EYE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_GROUND_TRAP_KEY
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
                attributes = Skill.Attributes(requiredDexterity = 25) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.EAGLE_EYE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.EAGLE_EYE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                specializationId = beastMaster.id,
                multiplier = 1.2,
                duration = 3,
                refreshTime = 4,
                minLevel = 16,
                imageName = SKILL_EAGLE_EYE_KEY,
                attributes = Skill.Attributes(requiredDexterity = 26) 
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
                attributes = Skill.Attributes(requiredDexterity = 28) 
            )
        )

        return CreateSpecializationResult(beastMaster, skills)
    }
}