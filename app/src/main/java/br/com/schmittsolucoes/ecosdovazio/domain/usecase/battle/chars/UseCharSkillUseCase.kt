package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import kotlin.math.max

class UseCharSkillUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase
) {
    operator fun invoke(
        skillInfo: UsedCharSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): CharSkillUsageResult {
        return when (skillInfo) {
            is UsedCharSkillInfo.CommonDamage -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = battleCharInfo,
                    battleMobInfo = battleMobInfo
                )

                val newEnemyHealth = max(battleMobInfo.actualHealth - damage, 0)

                CharSkillUsageResult.CommonDamage(
                    newEnemyHealth = newEnemyHealth,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedCharSkillInfo.DamageOverTime -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = battleCharInfo,
                    battleMobInfo = battleMobInfo
                )

                val newEnemyHealth = max(battleMobInfo.actualHealth - damage, 0)

                CharSkillUsageResult.DamageOverTime(
                    newEnemyHealth = newEnemyHealth,
                    repeat = skillInfo.duration,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedCharSkillInfo.Buff -> {
                TODO("Not yet implemented")
            }


            is UsedCharSkillInfo.Debuff -> {
                TODO("Not yet implemented")
            }
        }
    }
}