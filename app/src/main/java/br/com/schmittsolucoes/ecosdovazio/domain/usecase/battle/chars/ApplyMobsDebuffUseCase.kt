package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyMobsDebuffResult

class ApplyMobsDebuffUseCase {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyMobsDebuffResult {
        val newMobsDebuffs = mutableMapOf<String, List<CharActiveStatus.Debuff>>()

        mobs.forEach { (phaseMobId, mobInfo) ->
            val updatedDebuffs = mutableListOf<CharActiveStatus.Debuff>()

            mobInfo.activeStatus.filterIsInstance<CharActiveStatus.Debuff>().forEach { debuff ->
                if (debuff.remainingTurns > 1) {
                    updatedDebuffs.add(debuff.copy(remainingTurns = debuff.remainingTurns - 1))
                }
            }

            if (updatedDebuffs.isNotEmpty()) {
                newMobsDebuffs[phaseMobId] = updatedDebuffs
            }
        }

        return ApplyMobsDebuffResult(
            debuffs = newMobsDebuffs
        )
    }
}
