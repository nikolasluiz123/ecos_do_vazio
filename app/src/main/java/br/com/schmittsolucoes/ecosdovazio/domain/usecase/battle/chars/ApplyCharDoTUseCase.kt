package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.ApplyCharDoTResult
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobSkillDamageUseCase
import kotlin.math.max

class ApplyCharDoTUseCase(
    private val getMobSkillDamageUseCase: GetMobSkillDamageUseCase
) {
    operator fun invoke(
        battleCharInfo: BattleCharInfo,
        mobs: Map<String, BattleMobInfo>
    ): ApplyCharDoTResult {
        var currentHealth = battleCharInfo.actualHealth
        val updatedDots = mutableListOf<MobActiveStatus.DoT>()

        battleCharInfo.activeStatus.filterIsInstance<MobActiveStatus.DoT>().forEach { dot ->
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
                updatedDots.add(dot.copy(remainingTurns = dot.remainingTurns - 1))
            }
        }

        return ApplyCharDoTResult(
            charHealth = currentHealth,
            dots = updatedDots
        )
    }
}
