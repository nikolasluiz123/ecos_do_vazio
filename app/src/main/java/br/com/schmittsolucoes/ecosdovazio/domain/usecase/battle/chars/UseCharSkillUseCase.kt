package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.CalculateMobMultipliersUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.SkillException
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong

class UseCharSkillUseCase(
    private val getCharSkillDamageUseCase: GetCharSkillDamageUseCase,
    private val calculateCharMultipliersUseCase: CalculateCharMultipliersUseCase,
    private val calculateMobMultipliersUseCase: CalculateMobMultipliersUseCase
) {
    operator fun invoke(
        skillInfo: UsedCharSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): CharSkillUsageResult {
        val charMultipliers = calculateCharMultipliersUseCase(battleCharInfo)
        val mobMultipliers = calculateMobMultipliersUseCase(battleMobInfo)

        val actualCharInfo = battleCharInfo.copy(
            offensiveMultiplier = charMultipliers.offensive,
            defensiveMultiplier = charMultipliers.defensive
        )

        val actualMobInfo = battleMobInfo.copy(
            offensiveMultiplier = mobMultipliers.offensive,
            defensiveMultiplier = mobMultipliers.defensive
        )

        return when (skillInfo) {
            is UsedCharSkillInfo.CommonDamage -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = actualCharInfo,
                    battleMobInfo = actualMobInfo
                )

                val newEnemyHealth = getNewEnemyHealth(actualMobInfo, damage)

                CharSkillUsageResult.CommonDamage(
                    newEnemyHealth = newEnemyHealth,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedCharSkillInfo.VampiricDamage -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = actualCharInfo,
                    battleMobInfo = actualMobInfo
                )

                val newEnemyHealth = getNewEnemyHealth(actualMobInfo, damage)
                val calculatedCharHealth = actualCharInfo.actualHealth + (damage * skillInfo.multiplier).roundToLong()
                val newCharHealth = min(calculatedCharHealth, actualCharInfo.totalHealth)

                CharSkillUsageResult.VampiricDamage(
                    newEnemyHealth = newEnemyHealth,
                    newCharHealth = newCharHealth,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedCharSkillInfo.DamageOverTime -> {
                val damage = getCharSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = actualCharInfo,
                    battleMobInfo = actualMobInfo
                )

                val newEnemyHealth = getNewEnemyHealth(actualMobInfo, damage)

                CharSkillUsageResult.DamageOverTime(
                    newEnemyHealth = newEnemyHealth,
                    repeat = skillInfo.duration,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedCharSkillInfo.Buff -> {
                when (skillInfo.skillCategory) {
                    SkillCategory.OFFENSIVE_BUFF -> {
                        CharSkillUsageResult.Buff(
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
                        )
                    }

                    SkillCategory.DEFENSIVE_BUFF -> {
                        CharSkillUsageResult.Buff(
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
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
                        val newOffensiveMultiplier = mobMultipliers.offensive - skillInfo.multiplier

                        val mobWithNewDebuff = actualMobInfo.copy(
                            offensiveMultiplier = newOffensiveMultiplier
                        )

                        val damage = getCharSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = actualCharInfo,
                            battleMobInfo = mobWithNewDebuff
                        )

                        val newEnemyHealth = getNewEnemyHealth(mobWithNewDebuff, damage)

                        CharSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
                        )
                    }

                    SkillCategory.DEFENSIVE_DEBUFF -> {
                        val newDefensiveMultiplier = mobMultipliers.defensive - skillInfo.multiplier

                        val mobWithNewDebuff = actualMobInfo.copy(
                            defensiveMultiplier = newDefensiveMultiplier
                        )

                        val damage = getCharSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = actualCharInfo,
                            battleMobInfo = mobWithNewDebuff
                        )

                        val newEnemyHealth = getNewEnemyHealth(mobWithNewDebuff, damage)

                        CharSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            repeat = skillInfo.duration,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
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