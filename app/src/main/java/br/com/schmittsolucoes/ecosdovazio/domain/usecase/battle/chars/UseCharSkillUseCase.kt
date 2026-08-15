package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharSkillDamageUseCase
import kotlin.math.min

class UseCharSkillUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase
) {
    operator fun invoke(
        skillInfo: UsedSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): CharSkillUsageResult {
        return when (skillInfo) {
            is UsedSkillInfo.CommonDamage -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    classCategory = battleCharInfo.classCategory,
                    multiplier = battleCharInfo.multiplier,
                    attributes = battleCharInfo.attributes,
                    skillDamage = skillInfo.damage
                )

                val newEnemyHealth = min(battleMobInfo.actualHealth - damage, 0)

                CharSkillUsageResult.CommonDamage(
                    newEnemyHealth = newEnemyHealth,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedSkillInfo.DamageOverTime -> {
                TODO("Not yet implemented")
            }

            is UsedSkillInfo.Buff -> {
                TODO("Not yet implemented")
            }


            is UsedSkillInfo.Debuff -> {
                TODO("Not yet implemented")
            }
        }
    }
}