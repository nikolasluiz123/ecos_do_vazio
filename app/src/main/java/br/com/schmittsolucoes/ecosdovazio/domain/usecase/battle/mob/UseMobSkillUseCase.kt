package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
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
        var actualOffensiveMultiplier = battleCharInfo.offensiveMultiplier

        battleCharInfo.activeStatus.forEach { status ->
            when (status) {
                is MobActiveStatus.Debuff if status.skillCategory == SkillCategory.DEFENSIVE_DEBUFF -> {
                    actualDefensiveMultiplier -= status.skillInfo.multiplier
                }

                is MobActiveStatus.Debuff if status.skillCategory == SkillCategory.OFFENSIVE_DEBUFF -> {
                    actualOffensiveMultiplier -= status.skillInfo.multiplier
                }

                is CharActiveStatus.Buff if status.skillCategory == SkillCategory.DEFENSIVE_BUFF -> {
                    actualDefensiveMultiplier += status.skillInfo.multiplier
                }

                is CharActiveStatus.Buff if status.skillCategory == SkillCategory.OFFENSIVE_BUFF -> {
                    actualOffensiveMultiplier += status.skillInfo.multiplier
                }

                else -> {}
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
                    SkillCategory.OFFENSIVE_BUFF -> {
                        val newOffensiveMultiplier = actualOffensiveMultiplier + skillInfo.multiplier

                        MobSkillUsageResult.Buff(
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newOffensiveMultiplier,
                            skillId = skillInfo.skillId,
                            mobId = battleMobInfo.phaseMobId
                        )
                    }

                    SkillCategory.DEFENSIVE_BUFF -> {
                        val newDefensiveMultiplier = actualDefensiveMultiplier + skillInfo.multiplier

                        MobSkillUsageResult.Buff(
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newDefensiveMultiplier,
                            skillId = skillInfo.skillId,
                            mobId = battleMobInfo.phaseMobId
                        )
                    }

                    else -> {
                        throw SkillException.SkillCategoryNotHandled()
                    }
                }
            }


            is UsedMobSkillInfo.Debuff -> {
                when (skillInfo.skillCategory) {
                    SkillCategory.OFFENSIVE_DEBUFF -> {
                        val newOffensiveMultiplier = actualOffensiveMultiplier - skillInfo.multiplier

                        val battleCharInfo = battleCharInfo.copy(
                            offensiveMultiplier = newOffensiveMultiplier
                        )

                        val damage = getMobSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = battleCharInfo,
                            battleMobInfo = battleMobInfo
                        )

                        val newEnemyHealth = getNewEnemyHealth(battleCharInfo, damage)

                        MobSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newOffensiveMultiplier,
                            skillId = skillInfo.skillId
                        )
                    }

                    SkillCategory.DEFENSIVE_DEBUFF -> {
                        val newDefensiveMultiplier = actualDefensiveMultiplier - skillInfo.multiplier

                        val battleCharInfo = battleCharInfo.copy(
                            defensiveMultiplier = newDefensiveMultiplier
                        )

                        val damage = getMobSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = battleCharInfo,
                            battleMobInfo = battleMobInfo
                        )

                        val newEnemyHealth = getNewEnemyHealth(battleCharInfo, damage)

                        MobSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newDefensiveMultiplier,
                            skillId = skillInfo.skillId
                        )
                    }

                    else -> {
                        throw SkillException.SkillCategoryNotHandled()
                    }
                }
            }
        }
    }

    private fun getNewEnemyHealth(battleCharInfo: BattleCharInfo, damage: Long): Long {
        return max(battleCharInfo.actualHealth - damage, 0)
    }
}
