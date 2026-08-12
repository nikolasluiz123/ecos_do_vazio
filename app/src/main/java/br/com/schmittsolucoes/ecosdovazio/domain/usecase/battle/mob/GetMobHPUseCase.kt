package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

class GetMobHPUseCase {
    operator fun invoke(mobCategory: MobCategory, vitality: Long): Long {
        val baseValue = getBaseValue(mobCategory)
        val multiplier = getMultiplier(mobCategory)
        return baseValue + (vitality * multiplier)
    }

    private fun getBaseValue(mobCategory: MobCategory): Long {
        return when (mobCategory) {
            MobCategory.WARRIOR -> 40
            MobCategory.MAGE -> 30
            MobCategory.HEALER -> 30
            MobCategory.ORC_WARRIOR -> 120
        }
    }

    private fun getMultiplier(mobCategory: MobCategory): Long {
        return when (mobCategory) {
            MobCategory.WARRIOR -> 8
            MobCategory.MAGE -> 3
            MobCategory.HEALER -> 4
            MobCategory.ORC_WARRIOR -> 15
        }
    }
}