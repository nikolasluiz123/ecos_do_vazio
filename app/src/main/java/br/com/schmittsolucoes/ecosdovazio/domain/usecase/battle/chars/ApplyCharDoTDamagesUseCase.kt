package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDoT
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyCharDoTDamageResult
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobSkillDamageUseCase
import kotlin.math.max

class ApplyCharDoTDamagesUseCase(
    private val getMobSkillDamageUseCase: GetMobSkillDamageUseCase
) {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyCharDoTDamageResult {
        var currentHealth = battleCharInfo.actualHealth
        val updatedDots = mutableListOf<ActiveDoT.MobActiveDoT>()

        battleCharInfo.activeDots.forEach { dot ->
            if (currentHealth > 0) {
                val mobInfo = mobs[dot.sourceId] ?: return@forEach

                val damage = getMobSkillDamageUseCase.executeInternal(
                    skillInfo = dot.skillInfo,
                    battleCharInfo = battleCharInfo.copy(actualHealth = currentHealth),
                    battleMobInfo = mobInfo
                )

                currentHealth = max(currentHealth - damage, 0L)
            }

            if (dot.remainingTurns > 1) {
                updatedDots.add(dot.copyWithRemainingTurns(dot.remainingTurns - 1) as ActiveDoT.MobActiveDoT)
            }
        }

        return ApplyCharDoTDamageResult(
            charHealth = currentHealth,
            charDots = updatedDots
        )
    }
}
