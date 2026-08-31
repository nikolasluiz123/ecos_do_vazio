package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory.HEALER
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory.MAGE
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory.ORC_WARRIOR
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory.WARRIOR
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetTotalPointsCountUseCase
import kotlin.math.roundToLong

class GetMobPointsCountByLevelUseCase(
    private val getTotalPointsCountUseCase: GetTotalPointsCountUseCase
) {
    fun executeInternal(mobCategory: MobCategory, level: Long): Long {
        val points = getTotalPointsCountUseCase.executeInternal(level)
        val multiplier = getMultiplier(mobCategory)
        return (points * multiplier).roundToLong()
    }

    private fun getMultiplier(mobCategory: MobCategory): Double {
        return when (mobCategory) {
            WARRIOR, MAGE, HEALER -> {
                1.0
            }

            ORC_WARRIOR -> {
                1.5
            }
        }
    }
}