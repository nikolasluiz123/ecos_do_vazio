package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_GOBLIN_HEALER_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateMobResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateGoblinHealerMobUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(): CreateMobResult {
        val mob = Mob(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.GOBLIN_HEALER_MOB_NAME,
            descriptionTranslationId = TranslationIdentifier.GOBLIN_HEALER_MOB_DESCRIPTION,
            imageName = BATTLE_IMAGE_GOBLIN_HEALER_KEY,
            mobCategory = MobCategory.HEALER,
            attributes = Mob.Attributes(
                intelligence = 5,
                dexterity = 3,
                agility = 2,
                physicalResistance = 1,
                magicResistance = 1,
                vitality = 1
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.HEALING_TOUCH_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.HEALING_TOUCH_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.HEAL,
                mobId = mob.id,
                lifeRestore = 20,
                refreshTime = 3,
                minLevel = 1,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.REGENERATION_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.REGENERATION_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.AREA_HEAL,
                mobId = mob.id,
                lifeRestore = 15,
                refreshTime = 6,
                minLevel = 6,
                imageName = "",
                attributes = Skill.Attributes()
            )
        )

        return CreateMobResult(mob, skills)
    }
}