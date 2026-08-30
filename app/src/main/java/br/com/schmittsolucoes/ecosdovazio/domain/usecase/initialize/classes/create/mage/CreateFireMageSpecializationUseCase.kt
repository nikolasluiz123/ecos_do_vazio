package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_FIRE_MAGE_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.FIRE_MAGE_SPECIALIZATION_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.FIRE_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_FIREBALL_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_FIRE_BLAST_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.SKILL_SKIN_ON_FIRE_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateSpecializationResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateFireMageSpecializationUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(classId: String): CreateSpecializationResult {
        val fireMage = Specialization(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.FIRE_ELEMENTAL_SPEC_NAME.name,
            descriptionTranslationId = TranslationIdentifier.FIRE_ELEMENTAL_SPEC_DESCRIPTION.name,
            classId = classId,
            images = Specialization.Images(
                battleImageName = BATTLE_IMAGE_FIRE_MAGE_SPECIALIZATION_IMAGE_KEY,
                presentationImageName = FIRE_MAGE_SPECIALIZATION_IMAGE_KEY,
                profileImageName = FIRE_MAGE_SPECIALIZATION_PROFILE_IMAGE_KEY
            ),
            attributes = Specialization.Attributes(
                incrementIntelligence = 7,
                incrementDexterity = 3
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.FIREBALL_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.FIREBALL_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = fireMage.id,
                damage = 45,
                refreshTime = 3,
                minLevel = 15,
                imageName = SKILL_FIREBALL_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 25) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.IGNEOUS_EXPLOSION_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.IGNEOUS_EXPLOSION_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                specializationId = fireMage.id,
                damage = 30,
                refreshTime = 5,
                minLevel = 16,
                imageName = SKILL_FIRE_BLAST_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 26) 
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.FIRE_SKIN_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.FIRE_SKIN_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_BUFF,
                specializationId = fireMage.id,
                multiplier = 1.2,
                duration = 3,
                refreshTime = 6,
                minLevel = 18,
                imageName = SKILL_SKIN_ON_FIRE_KEY,
                attributes = Skill.Attributes(requiredIntelligence = 28) 
            )
        )

        return CreateSpecializationResult(fireMage, skills)
    }
}