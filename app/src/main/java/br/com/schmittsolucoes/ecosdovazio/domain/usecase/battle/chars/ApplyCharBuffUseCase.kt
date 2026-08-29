package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyCharBuffResult

class ApplyCharBuffUseCase {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyCharBuffResult {
        val updatedBuffs = mutableListOf<CharActiveStatus.Buff>()

        battleCharInfo.activeStatus.filterIsInstance<CharActiveStatus.Buff>().forEach { buff ->
            if (buff.remainingTurns > 1) {
                updatedBuffs.add(buff.copy(remainingTurns = buff.remainingTurns - 1))
            }
        }

        return ApplyCharBuffResult(
            buffs = updatedBuffs
        )
    }
}
