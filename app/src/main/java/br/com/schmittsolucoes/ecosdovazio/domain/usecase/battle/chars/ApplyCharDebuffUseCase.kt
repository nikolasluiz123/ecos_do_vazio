package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyCharDebuffResult

class ApplyCharDebuffUseCase {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyCharDebuffResult {
        val updatedDebuffs = mutableListOf<MobActiveStatus.Debuff>()

        battleCharInfo.activeStatus.filterIsInstance<MobActiveStatus.Debuff>().forEach { debuff ->
            if (debuff.remainingTurns > 1) {
                updatedDebuffs.add(debuff.copy(remainingTurns = debuff.remainingTurns - 1))
            }
        }

        return ApplyCharDebuffResult(
            debuffs = updatedDebuffs
        )
    }
}
