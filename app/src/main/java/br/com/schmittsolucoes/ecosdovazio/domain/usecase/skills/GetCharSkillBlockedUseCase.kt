package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill

class GetCharSkillBlockedUseCase {
    operator fun invoke(battleChar: BattleChar, skillRequiredAttributes: CharSkill.Attributes): Boolean {
        val skillRequirements = mapOf(
            battleChar.strength.value to skillRequiredAttributes.requiredStrength,
            battleChar.dexterity.value to skillRequiredAttributes.requiredDexterity,
            battleChar.intelligence.value to skillRequiredAttributes.requiredIntelligence,
            battleChar.physicalResistance.value to skillRequiredAttributes.requiredPhysicalResistance,
            battleChar.magicResistance.value to skillRequiredAttributes.requiredMagicResistance,
            battleChar.vitality.value to skillRequiredAttributes.requiredVitality,
            battleChar.agility.value to skillRequiredAttributes.requiredAgility,
        )

        return skillRequirements.any { (charValue, skillRequirement) ->
            charValue < skillRequirement
        }
    }
}