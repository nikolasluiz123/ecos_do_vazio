package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_GOBLIN_XAMA_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateMobResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateGoblinShamanMobUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(): CreateMobResult {
        val mob = Mob(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.GOBLIN_SHAMAN_MOB_NAME,
            descriptionTranslationId = TranslationIdentifier.GOBLIN_SHAMAN_MOB_NAME,
            imageName = BATTLE_IMAGE_GOBLIN_XAMA_KEY,
            mobCategory = MobCategory.MAGE,
            attributes = Mob.Attributes(
                intelligence = 6,
                dexterity = 3,
                vitality = 1,
                magicResistance = 1
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.MYSTIC_PROJECTILE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.MYSTIC_PROJECTILE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 15,
                refreshTime = 1,
                minLevel = 1,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.CURSE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.CURSE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE_OVER_TIME,
                mobId = mob.id,
                damage = 5,
                duration = 3,
                refreshTime = 4,
                minLevel = 3,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.ENERGY_SHIELD_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.ENERGY_SHIELD_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_BUFF,
                mobId = mob.id,
                multiplier = 1.2,
                duration = 2,
                refreshTime = 5,
                minLevel = 6,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.MANA_DRAIN_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.MANA_DRAIN_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 20,
                refreshTime = 4,
                minLevel = 10,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.ARCANE_EXPLOSION_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.ARCANE_EXPLOSION_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 30,
                refreshTime = 5,
                minLevel = 15,
                imageName = "",
                attributes = Skill.Attributes()
            )
        )

        return CreateMobResult(mob, skills)
    }
}