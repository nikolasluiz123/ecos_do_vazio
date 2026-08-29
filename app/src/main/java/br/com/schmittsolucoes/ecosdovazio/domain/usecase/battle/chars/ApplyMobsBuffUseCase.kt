package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyMobsBuffResult

class ApplyMobsBuffUseCase {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyMobsBuffResult {
        val newMobsBuffs = mutableMapOf<String, List<MobActiveStatus.Buff>>()

        mobs.forEach { (phaseMobId, mobInfo) ->
            val updatedBuffs = mutableListOf<MobActiveStatus.Buff>()

            mobInfo.activeStatus.filterIsInstance<MobActiveStatus.Buff>().forEach { buff ->
                if (buff.remainingTurns > 1) {
                    updatedBuffs.add(buff.copy(remainingTurns = buff.remainingTurns - 1))
                }
            }

            if (updatedBuffs.isNotEmpty()) {
                newMobsBuffs[phaseMobId] = updatedBuffs
            }
        }

        return ApplyMobsBuffResult(
            buffs = newMobsBuffs
        )
    }
}
