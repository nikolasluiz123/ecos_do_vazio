package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.BattleMultipliers
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo

class CalculateMobMultipliersUseCase {
    operator fun invoke(battleMobInfo: BattleMobInfo): BattleMultipliers {
        var offensiveMultiplier = battleMobInfo.offensiveMultiplier
        var defensiveMultiplier = battleMobInfo.defensiveMultiplier

        battleMobInfo.activeStatus.forEach { status ->
            when (status) {
                is MobActiveStatus.Buff -> {
                    when (status.skillCategory) {
                        SkillCategory.OFFENSIVE_BUFF -> offensiveMultiplier += status.skillInfo.multiplier
                        SkillCategory.DEFENSIVE_BUFF -> defensiveMultiplier += status.skillInfo.multiplier
                        else -> {}
                    }
                }
                is CharActiveStatus.Debuff -> {
                    when (status.skillCategory) {
                        SkillCategory.OFFENSIVE_DEBUFF -> offensiveMultiplier -= status.skillInfo.multiplier
                        SkillCategory.DEFENSIVE_DEBUFF -> defensiveMultiplier -= status.skillInfo.multiplier
                        else -> {}
                    }
                }
                else -> {}
            }
        }

        return BattleMultipliers(
            offensive = offensiveMultiplier,
            defensive = defensiveMultiplier
        )
    }
}
