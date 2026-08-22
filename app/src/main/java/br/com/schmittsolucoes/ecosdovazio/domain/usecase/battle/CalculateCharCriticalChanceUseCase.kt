package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

class CalculateCharCriticalChanceUseCase {
    fun executeInternal(dexterityPoints: Long, category: ClassCategory): Double {
        val factor = getFactor(category)
        val maxChance = getMaxCriticalChance(category)
        val chance = dexterityPoints * factor

        return minOf(maxChance, chance)
    }

    private fun getFactor(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.003
            ClassCategory.MAGE -> 0.005
            ClassCategory.ARCHER -> 0.008
        }
    }

    private fun getMaxCriticalChance(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.25
            ClassCategory.MAGE -> 0.4
            ClassCategory.ARCHER -> 0.6
        }
    }
}
