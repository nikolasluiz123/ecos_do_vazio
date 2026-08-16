package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import kotlin.math.max

class UseMobSkillUseCase(
    private val getMobSkillDamageUseCase: GetMobSkillDamageUseCase
) {
    fun executeInternal(
        skillInfo: UsedMobSkillInfo,
        battleMobInfo: BattleMobInfo,
        battleCharInfo: BattleCharInfo
    ): MobSkillUsageResult {
        return when (skillInfo) {
            is UsedMobSkillInfo.CommonDamage -> {
                val damage = getMobSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = battleCharInfo,
                    battleMobInfo = battleMobInfo
                )

                val newEnemyHealth = max(battleCharInfo.actualHealth - damage, 0)

                MobSkillUsageResult.CommonDamage(
                    newEnemyHealth = newEnemyHealth,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedMobSkillInfo.DamageOverTime -> {
                TODO("Not yet implemented")
            }

            is UsedMobSkillInfo.Buff -> {
                TODO("Not yet implemented")
            }


            is UsedMobSkillInfo.Debuff -> {
                TODO("Not yet implemented")
            }
        }
    }
}
