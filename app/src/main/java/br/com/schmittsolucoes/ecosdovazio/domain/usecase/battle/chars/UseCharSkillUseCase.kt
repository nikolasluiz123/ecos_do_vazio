package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.SkillException
import kotlin.math.max

class UseCharSkillUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase
) {
    operator fun invoke(
        skillInfo: UsedCharSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): CharSkillUsageResult {
        var actualDefensiveMultiplier = battleMobInfo.defensiveMultiplier

        battleMobInfo.activeStatus.forEach { status ->
            when {
                status is CharActiveStatus.Debuff && status.skillCategory == SkillCategory.DEFENSIVE_DEBUFF -> {
                    actualDefensiveMultiplier -= status.skillInfo.multiplier
                }
            }
        }

        return when (skillInfo) {
            is UsedCharSkillInfo.CommonDamage -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = battleCharInfo,
                    battleMobInfo = battleMobInfo
                )

                val newEnemyHealth = getNewEnemyHealth(battleMobInfo, damage)

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

                val newEnemyHealth = getNewEnemyHealth(battleMobInfo, damage)

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
                when (skillInfo.skillCategory) {
                    SkillCategory.OFFENSIVE_DEBUFF -> TODO()
                    SkillCategory.DEFENSIVE_DEBUFF -> {
                        val newDefensiveMultiplier = actualDefensiveMultiplier - skillInfo.multiplier

                        val damage = getCharSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = battleCharInfo,
                            battleMobInfo = battleMobInfo.copy(
                                defensiveMultiplier = newDefensiveMultiplier
                            )
                        )

                        val newEnemyHealth = getNewEnemyHealth(battleMobInfo, damage)

                        CharSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newDefensiveMultiplier = newDefensiveMultiplier
                        )
                    }

                    else -> {
                        throw SkillException.SkillCategoryNotHandled()
                    }
                }
            }
        }
    }

    private fun getNewEnemyHealth(battleMobInfo: BattleMobInfo, damage: Long): Long {
        return max(battleMobInfo.actualHealth - damage, 0)
    }
}