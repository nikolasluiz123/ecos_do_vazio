package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create

import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_GOBLIN_WARRIOR_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CreateMobResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider

class CreateGoblinWarriorMobUseCase(
    private val identifierProvider: IdentifierProvider
) {
    fun executeInternal(): CreateMobResult {
        val mob = Mob(
            id = identifierProvider.generate(),
            nameTranslationId = TranslationIdentifier.GOBLIN_WARRIOR_MOB_NAME,
            descriptionTranslationId = TranslationIdentifier.GOBLIN_WARRIOR_MOB_NAME,
            imageName = BATTLE_IMAGE_GOBLIN_WARRIOR_KEY,
            mobCategory = MobCategory.WARRIOR,
            attributes = Mob.Attributes(
                strength = 6,
                dexterity = 3,
                vitality = 2,
                physicalResistance = 1
            )
        )

        val skills = listOf(
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.QUICK_ATTACK_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.QUICK_ATTACK_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 15,
                refreshTime = 1,
                minLevel = 1
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.BRUTAL_CUT_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.BRUTAL_CUT_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 20,
                refreshTime = 3,
                minLevel = 3
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.WILD_INSTINCT_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.WILD_INSTINCT_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                mobId = mob.id,
                multiplier = 1.1,
                duration = 2,
                refreshTime = 4,
                minLevel = 6
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.CHARGE_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.CHARGE_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.DAMAGE,
                mobId = mob.id,
                damage = 25,
                refreshTime = 4,
                minLevel = 10
            ),
            Skill(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.GOBLIN_FURY_SKILL_NAME,
                descriptionTranslationId = TranslationIdentifier.GOBLIN_FURY_SKILL_DESCRIPTION,
                skillCategory = SkillCategory.OFFENSIVE_BUFF,
                mobId = mob.id,
                multiplier = 1.2,
                duration = 3,
                refreshTime = 5,
                minLevel = 15
            )
        )

        return CreateMobResult(mob, skills)
    }
}