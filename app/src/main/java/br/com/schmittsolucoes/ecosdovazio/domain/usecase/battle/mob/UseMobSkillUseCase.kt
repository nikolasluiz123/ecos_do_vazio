package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.SkillException
import kotlin.math.max

class UseMobSkillUseCase(
    private val getMobSkillDamageUseCase: GetMobSkillDamageUseCase
) {
    fun executeInternal(
        skillInfo: UsedMobSkillInfo,
        battleMobInfo: BattleMobInfo,
        battleCharInfo: BattleCharInfo
    ): MobSkillUsageResult {
        var actualDefensiveMultiplier = battleCharInfo.defensiveMultiplier

        battleCharInfo.activeStatus.forEach { status ->
            when {
                status is MobActiveStatus.Debuff && status.skillCategory == SkillCategory.DEFENSIVE_DEBUFF -> {
                    actualDefensiveMultiplier -= status.skillInfo.multiplier
                }
            }
        }

        return when (skillInfo) {
            is UsedMobSkillInfo.CommonDamage -> {
                val damage = getMobSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = battleCharInfo,
                    battleMobInfo = battleMobInfo
                )

                val newEnemyHealth = getNewEnemyHealth(battleCharInfo, damage)

                MobSkillUsageResult.CommonDamage(
                    newEnemyHealth = newEnemyHealth,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedMobSkillInfo.DamageOverTime -> {
                val damage = getMobSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = battleCharInfo,
                    battleMobInfo = battleMobInfo
                )

                val newEnemyHealth = getNewEnemyHealth(battleCharInfo, damage)

                MobSkillUsageResult.DamageOverTime(
                    newEnemyHealth = newEnemyHealth,
                    repeat = skillInfo.duration,
                    refreshTime = skillInfo.refreshTime,
                    skillId = skillInfo.skillId
                )
            }

            is UsedMobSkillInfo.Buff -> {
                when (skillInfo.skillCategory) {
                    SkillCategory.OFFENSIVE_DEBUFF -> TODO()
                    SkillCategory.DEFENSIVE_DEBUFF -> {
                        val newDefensiveMultiplier = actualDefensiveMultiplier - skillInfo.multiplier

                        val damage = getMobSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = battleCharInfo,
                            battleMobInfo = battleMobInfo.copy(
                                defensiveMultiplier = newDefensiveMultiplier
                            )
                        )

                        val newEnemyHealth = getNewEnemyHealth(battleCharInfo, damage)

                        MobSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newDefensiveMultiplier = newDefensiveMultiplier,
                            skillId = skillInfo.skillId
                        )
                    }

                    else -> {
                        throw SkillException.SkillCategoryNotHandled()
                    }
                }
            }


            is UsedMobSkillInfo.Debuff -> {
                TODO("Not yet implemented")
            }
        }
    }

    private fun getNewEnemyHealth(battleCharInfo: BattleCharInfo, damage: Long): Long {
        return max(battleCharInfo.actualHealth - damage, 0)
    }
}
