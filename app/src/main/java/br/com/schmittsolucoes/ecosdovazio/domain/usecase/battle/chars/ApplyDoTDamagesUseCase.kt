package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import android.util.Log
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDot
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyDoTDamageResult
import kotlin.math.max

class ApplyDoTDamagesUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase
) {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyDoTDamageResult {
        val newMobsHealth = mutableMapOf<String, Long>()
        val newMobsDots = mutableMapOf<String, List<ActiveDot>>()

        mobs.forEach { (phaseMobId, mobInfo) ->
            var currentHealth = mobInfo.actualHealth
            val updatedDots = mutableListOf<ActiveDot>()

            mobInfo.activeDots.forEach { dot ->
                if (currentHealth > 0) {
                    val damage = getCharSkillDamageUseCase.executeInternal(
                        skillInfo = dot.skillInfo,
                        battleCharInfo = battleCharInfo,
                        battleMobInfo = mobInfo.copy(actualHealth = currentHealth)
                    )

                    currentHealth = max(currentHealth - damage, 0L)

                    if (dot.remainingTurns > 1 && currentHealth > 0) {
                        updatedDots.add(dot.copy(remainingTurns = dot.remainingTurns - 1))
                    }
                }
            }

            newMobsHealth[phaseMobId] = currentHealth

            if (updatedDots.isNotEmpty()) {
                newMobsDots[phaseMobId] = updatedDots
            }
        }

        return ApplyDoTDamageResult(
            mobsHealth = newMobsHealth,
            mobsDots = newMobsDots
        )
    }
}
