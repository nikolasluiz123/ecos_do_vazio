package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobXPInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.MOB_EXPERIENCE_FACTOR
import kotlin.math.pow
import kotlin.math.roundToLong

class CalculateHistoryPhaseExperienceUseCase {
    fun executeInternal(battleMobXPInfo: List<BattleMobXPInfo>): Long {
        return battleMobXPInfo.sumOf {
            val baseXP = getBaseXP(it.category)
            (baseXP * it.level.toDouble().pow(MOB_EXPERIENCE_FACTOR)).roundToLong()
        }
    }

    private fun getBaseXP(mobCategory: MobCategory): Long {
        return when (mobCategory) {
            MobCategory.WARRIOR -> 40
            MobCategory.MAGE -> 40
            MobCategory.HEALER -> 30
            MobCategory.ORC_WARRIOR -> 120
        }
    }
}