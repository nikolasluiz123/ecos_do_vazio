package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill

class GetCharSkillBlockedUseCase {
    operator fun invoke(
        battleChar: BattleChar,
        skillRequiredAttributes: CharSkill.Attributes,
        minLevel: Long
    ): Boolean {
        val skillRequirements = listOf(
            SkillRequirement(battleChar.strength.totalValue, skillRequiredAttributes.requiredStrength),
            SkillRequirement(battleChar.dexterity.totalValue, skillRequiredAttributes.requiredDexterity),
            SkillRequirement(battleChar.intelligence.totalValue, skillRequiredAttributes.requiredIntelligence),
            SkillRequirement(battleChar.physicalResistance.totalValue, skillRequiredAttributes.requiredPhysicalResistance),
            SkillRequirement(battleChar.magicResistance.totalValue, skillRequiredAttributes.requiredMagicResistance),
            SkillRequirement(battleChar.vitality.totalValue, skillRequiredAttributes.requiredVitality),
            SkillRequirement(battleChar.agility.totalValue, skillRequiredAttributes.requiredAgility),
        )

        val anyRequirementNotMet = skillRequirements.any { requirement ->
            requirement.charValue < requirement.requiredValue
        }

        return battleChar.level < minLevel || anyRequirementNotMet
    }

    private data class SkillRequirement(
        val charValue: Long,
        val requiredValue: Long
    )
}