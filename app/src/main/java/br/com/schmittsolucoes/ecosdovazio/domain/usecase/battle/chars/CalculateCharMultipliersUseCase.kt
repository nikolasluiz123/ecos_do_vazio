package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.BattleMultipliers
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

class CalculateCharMultipliersUseCase {
    operator fun invoke(battleCharInfo: BattleCharInfo): BattleMultipliers {
        var offensiveMultiplier = battleCharInfo.offensiveMultiplier
        var defensiveMultiplier = battleCharInfo.defensiveMultiplier

        battleCharInfo.activeStatus.forEach { status ->
            when (status) {
                is CharActiveStatus.Buff -> {
                    when (status.skillCategory) {
                        SkillCategory.OFFENSIVE_BUFF -> offensiveMultiplier += status.skillInfo.multiplier
                        SkillCategory.DEFENSIVE_BUFF -> defensiveMultiplier += status.skillInfo.multiplier
                        else -> {}
                    }
                }
                is MobActiveStatus.Debuff -> {
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
