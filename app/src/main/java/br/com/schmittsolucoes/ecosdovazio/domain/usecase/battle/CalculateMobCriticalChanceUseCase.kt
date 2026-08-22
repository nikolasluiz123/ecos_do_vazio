package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

class CalculateMobCriticalChanceUseCase {
    fun executeInternal(dexterityPoints: Long, category: MobCategory): Double {
        val factor = getFactor(category)
        val maxChance = getMaxCriticalChance(category)
        val chance = dexterityPoints * factor

        return minOf(maxChance, chance)
    }

    private fun getFactor(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 0.03
            MobCategory.MAGE -> 0.03
            MobCategory.HEALER -> 0.0
            MobCategory.ORC_WARRIOR -> 0.02
        }
    }

    private fun getMaxCriticalChance(category: MobCategory): Double {
        return when (category) {
            MobCategory.WARRIOR -> 0.2
            MobCategory.MAGE -> 0.2
            MobCategory.HEALER -> 0.0
            MobCategory.ORC_WARRIOR -> 0.15
        }
    }
}
