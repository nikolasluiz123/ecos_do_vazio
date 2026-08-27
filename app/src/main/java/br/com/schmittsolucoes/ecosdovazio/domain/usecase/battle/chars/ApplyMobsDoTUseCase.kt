package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyMobsDoTResult
import kotlin.math.max

class ApplyMobsDoTUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase
) {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyMobsDoTResult {
        val newMobsHealth = mutableMapOf<String, Long>()
        val newMobsDots = mutableMapOf<String, List<CharActiveStatus.DoT>>()

        mobs.forEach { (phaseMobId, mobInfo) ->
            var currentHealth = mobInfo.actualHealth
            val updatedDots = mutableListOf<CharActiveStatus.DoT>()

            mobInfo.activeStatus.filterIsInstance<CharActiveStatus.DoT>().forEach { dot ->
                if (currentHealth > 0) {
                    val damage = getCharSkillDamageUseCase.executeInternal(
                        skillInfo = dot.skillInfo,
                        battleCharInfo = battleCharInfo,
                        battleMobInfo = mobInfo.copy(actualHealth = currentHealth)
                    )

                    currentHealth = max(currentHealth - damage, 0L)
                }

                if (dot.remainingTurns > 1) {
                    updatedDots.add(dot.copy(remainingTurns = dot.remainingTurns - 1))
                }
            }

            newMobsHealth[phaseMobId] = currentHealth

            if (updatedDots.isNotEmpty()) {
                newMobsDots[phaseMobId] = updatedDots
            }
        }

        return ApplyMobsDoTResult(
            mobsHealth = newMobsHealth,
            dots = newMobsDots
        )
    }
}
