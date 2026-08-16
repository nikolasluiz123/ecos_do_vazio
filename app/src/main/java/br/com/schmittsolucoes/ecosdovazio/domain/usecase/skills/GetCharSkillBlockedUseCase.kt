package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill

class GetCharSkillBlockedUseCase {
    operator fun invoke(
        battleChar: BattleChar,
        skillRequiredAttributes: CharSkill.Attributes,
        minLevel: Long
    ): Boolean {
        val skillRequirements = mapOf(
            battleChar.strength.totalValue to skillRequiredAttributes.requiredStrength,
            battleChar.dexterity.totalValue to skillRequiredAttributes.requiredDexterity,
            battleChar.intelligence.totalValue to skillRequiredAttributes.requiredIntelligence,
            battleChar.physicalResistance.totalValue to skillRequiredAttributes.requiredPhysicalResistance,
            battleChar.magicResistance.totalValue to skillRequiredAttributes.requiredMagicResistance,
            battleChar.vitality.totalValue to skillRequiredAttributes.requiredVitality,
            battleChar.agility.totalValue to skillRequiredAttributes.requiredAgility,
        )

        if (battleChar.level < minLevel) {
            return true
        }

        return skillRequirements.any { (charValue, skillRequirement) ->
            charValue < skillRequirement
        }
    }
}