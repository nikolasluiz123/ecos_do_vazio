package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.SkillException
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong

class UseCharSkillUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase
) {
    operator fun invoke(
        skillInfo: UsedCharSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): CharSkillUsageResult {
        var actualDefensiveMultiplier = battleMobInfo.defensiveMultiplier
        var actualOffensiveMultiplier = battleMobInfo.offensiveMultiplier

        battleMobInfo.activeStatus.forEach { status ->
            when (status) {
                is CharActiveStatus.Debuff if status.skillCategory == SkillCategory.DEFENSIVE_DEBUFF -> {
                    actualDefensiveMultiplier -= status.skillInfo.multiplier
                }

                is CharActiveStatus.Debuff if status.skillCategory == SkillCategory.OFFENSIVE_DEBUFF -> {
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

            is UsedCharSkillInfo.VampiricDamage -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = battleCharInfo,
                    battleMobInfo = battleMobInfo
                )

                val newEnemyHealth = getNewEnemyHealth(battleMobInfo, damage)
                val calculatedCharHealth = battleCharInfo.actualHealth + (damage * skillInfo.multiplier).roundToLong()
                val newCharHealth = min(calculatedCharHealth, battleCharInfo.totalHealth)

                CharSkillUsageResult.VampiricDamage(
                    newEnemyHealth = newEnemyHealth,
                    newCharHealth = newCharHealth,
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
                when (skillInfo.skillCategory) {
                    SkillCategory.OFFENSIVE_BUFF -> {
                        val newOffensiveMultiplier = actualOffensiveMultiplier + skillInfo.multiplier

                        CharSkillUsageResult.Buff(
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newOffensiveMultiplier
                        )
                    }

                    SkillCategory.DEFENSIVE_BUFF -> {
                        val newDefensiveMultiplier = actualDefensiveMultiplier + skillInfo.multiplier

                        CharSkillUsageResult.Buff(
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newDefensiveMultiplier
                        )
                    }

                    else -> {
                        throw SkillException.SkillCategoryNotHandled()
                    }
                }
            }


            is UsedCharSkillInfo.Debuff -> {
                when (skillInfo.skillCategory) {
                    SkillCategory.OFFENSIVE_DEBUFF -> {
                        val newOffensiveMultiplier = actualOffensiveMultiplier - skillInfo.multiplier

                        val battleMobInfo = battleMobInfo.copy(
                            offensiveMultiplier = newOffensiveMultiplier
                        )

                        val damage = getCharSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = battleCharInfo,
                            battleMobInfo = battleMobInfo
                        )

                        val newEnemyHealth = getNewEnemyHealth(battleMobInfo, damage)

                        CharSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newOffensiveMultiplier
                        )
                    }

                    SkillCategory.DEFENSIVE_DEBUFF -> {
                        val newDefensiveMultiplier = actualDefensiveMultiplier - skillInfo.multiplier

                        val battleMobInfo = battleMobInfo.copy(
                            defensiveMultiplier = newDefensiveMultiplier
                        )

                        val damage = getCharSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = battleCharInfo,
                            battleMobInfo = battleMobInfo
                        )

                        val newEnemyHealth = getNewEnemyHealth(battleMobInfo, damage)

                        CharSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            refreshTime = skillInfo.refreshTime,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            newMultiplier = newDefensiveMultiplier
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