package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

class CalculateCharDodgeChanceUseCase {
    fun executeInternal(agilityPoints: Long, category: ClassCategory): Double {
        val factor = getFactor(category)
        val maxChance = getMaxDodgeChance(category)
        val chance = agilityPoints * factor

        return minOf(maxChance, chance)
    }

    private fun getFactor(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.005
            ClassCategory.MAGE -> 0.005
            ClassCategory.ARCHER -> 0.012
        }
    }

    private fun getMaxDodgeChance(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.15
            ClassCategory.MAGE -> 0.15
            ClassCategory.ARCHER -> 0.45
        }
    }
}
