package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_ORC_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateMobResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateCaveOrcMobUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(): CreateMobResult {
        val mob = Mob(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.CAVE_ORC_MOB_NAME,
            descriptionTranslationId = TranslationIdentifier.CAVE_ORC_MOB_DESCRIPTION,
            imageName = BATTLE_IMAGE_ORC_KEY,
            mobCategory = MobCategory.ORC_WARRIOR,
            attributes = Mob.Attributes(
                strength = 12,
                dexterity = 2,
                vitality = 10,
                physicalResistance = 8,
                magicResistance = 5
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.GIANT_SLAP_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.GIANT_SLAP_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 30,
                refreshTime = 1,
                minLevel = 1,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.THICK_SKIN_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.THICK_SKIN_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DEFENSIVE_BUFF,
                mobId = mob.id,
                multiplier = 1.3,
                duration = 3,
                refreshTime = 6,
                minLevel = 5,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.SMASH_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.SMASH_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 40,
                refreshTime = 4,
                minLevel = 10,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.THREATENING_ROAR_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.THREATENING_ROAR_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                mobId = mob.id,
                multiplier = 1.2,
                duration = 2,
                refreshTime = 5,
                minLevel = 15,
                imageName = "",
                attributes = Skill.Attributes()
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BOSS_STRIKE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BOSS_STRIKE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 50,
                refreshTime = 6,
                minLevel = 20,
                imageName = "",
                attributes = Skill.Attributes()
            )
        )

        return CreateMobResult(mob, skills)
    }
}