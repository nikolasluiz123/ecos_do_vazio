package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDoT
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyMobsDoTDamageResult
import kotlin.math.max

class ApplyMobsDoTDamagesUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase
) {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyMobsDoTDamageResult {
        val newMobsHealth = mutableMapOf<String, Long>()
        val newMobsDots = mutableMapOf<String, List<ActiveDoT.CharActiveDoT>>()

        mobs.forEach { (phaseMobId, mobInfo) ->
            var currentHealth = mobInfo.actualHealth
            val updatedDots = mutableListOf<ActiveDoT.CharActiveDoT>()

            mobInfo.activeDots.forEach { dot ->
                if (currentHealth > 0) {
                    val damage = getCharSkillDamageUseCase.executeInternal(
                        skillInfo = dot.skillInfo,
                        battleCharInfo = battleCharInfo,
                        battleMobInfo = mobInfo.copy(actualHealth = currentHealth)
                    )

                    currentHealth = max(currentHealth - damage, 0L)
                }

                if (dot.remainingTurns > 1) {
                    updatedDots.add(dot.copyWithRemainingTurns(dot.remainingTurns - 1) as ActiveDoT.CharActiveDoT)
                }
            }

            newMobsHealth[phaseMobId] = currentHealth

            if (updatedDots.isNotEmpty()) {
                newMobsDots[phaseMobId] = updatedDots
            }
        }

        return ApplyMobsDoTDamageResult(
            mobsHealth = newMobsHealth,
            mobsDots = newMobsDots
        )
    }
}
