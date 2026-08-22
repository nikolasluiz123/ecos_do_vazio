package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

class CalculateMobDodgeChanceUseCase {
    fun executeInternal(agilityPoints: Long, category: MobCategory): Double {
        val factor = getFactor(category)
        val maxChance = getMaxDodgeChance(category)
        val chance = agilityPoints * factor

        return minOf(maxChance, chance)
    }

    private fun getFactor(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 0.005
            MobCategory.MAGE -> 0.005
            MobCategory.HEALER -> 0.01
            MobCategory.ORC_WARRIOR -> 0.005
        }
    }

    private fun getMaxDodgeChance(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 0.1
            MobCategory.MAGE -> 0.1
            MobCategory.HEALER -> 0.3
            MobCategory.ORC_WARRIOR -> 0.10
        }
    }
}
