package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.CalculateCharMultipliersUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.SkillException
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong

class UseMobSkillUseCase(
    private val getMobSkillDamageUseCase: GetMobSkillDamageUseCase,
    private val calculateCharMultipliersUseCase: CalculateCharMultipliersUseCase,
    private val calculateMobMultipliersUseCase: CalculateMobMultipliersUseCase
) {
    fun executeInternal(
        skillInfo: UsedMobSkillInfo,
        battleMobInfo: BattleMobInfo,
        battleCharInfo: BattleCharInfo,
        liveMobs: List<BattleMobInfo>
    ): MobSkillUsageResult {
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
            is UsedMobSkillInfo.CommonDamage -> {
                val damage = getMobSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = actualCharInfo,
                    battleMobInfo = actualMobInfo
                )

                val newEnemyHealth = getNewEnemyHealthApplyingDamage(actualCharInfo, damage)

                MobSkillUsageResult.CommonDamage(
                    newEnemyHealth = newEnemyHealth,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedMobSkillInfo.DamageOverTime -> {
                val damage = getMobSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = actualCharInfo,
                    battleMobInfo = actualMobInfo
                )

                val newEnemyHealth = getNewEnemyHealthApplyingDamage(actualCharInfo, damage)

                MobSkillUsageResult.DamageOverTime(
                    newEnemyHealth = newEnemyHealth,
                    repeat = skillInfo.duration,
                    refreshTime = skillInfo.refreshTime,
                    skillId = skillInfo.skillId
                )
            }

            is UsedMobSkillInfo.VampiricDamage -> {
                val damage = getMobSkillDamageUseCase.executeInternal(
                    skillInfo = skillInfo,
                    battleCharInfo = actualCharInfo,
                    battleMobInfo = actualMobInfo
                )

                val newEnemyHealth = getNewEnemyHealthApplyingDamage(actualCharInfo, damage)
                val calculatedMobHealth = actualMobInfo.actualHealth + (damage * skillInfo.multiplier).roundToLong()
                val newMobHealth = min(calculatedMobHealth, actualMobInfo.totalHealth)

                MobSkillUsageResult.VampiricDamage(
                    newEnemyHealth = newEnemyHealth,
                    newCharHealth = newMobHealth,
                    mobId = actualMobInfo.phaseMobId,
                    refreshTime = skillInfo.refreshTime
                )
            }

            is UsedMobSkillInfo.Buff -> {
                when (skillInfo.skillCategory) {
                    SkillCategory.OFFENSIVE_BUFF -> {
                        MobSkillUsageResult.Buff(
                            repeat = skillInfo.duration,
                            skillId = skillInfo.skillId,
                            mobId = actualMobInfo.phaseMobId,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
                        )
                    }

                    SkillCategory.DEFENSIVE_BUFF -> {
                        MobSkillUsageResult.Buff(
                            repeat = skillInfo.duration,
                            skillId = skillInfo.skillId,
                            mobId = actualMobInfo.phaseMobId,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
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
                        val newOffensiveMultiplier = charMultipliers.offensive - skillInfo.multiplier

                        val charWithNewDebuff = actualCharInfo.copy(
                            offensiveMultiplier = newOffensiveMultiplier
                        )

                        val damage = getMobSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = charWithNewDebuff,
                            battleMobInfo = actualMobInfo
                        )

                        val newEnemyHealth = getNewEnemyHealthApplyingDamage(charWithNewDebuff, damage)

                        MobSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            repeat = skillInfo.duration,
                            skillId = skillInfo.skillId,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
                        )
                    }

                    SkillCategory.DEFENSIVE_DEBUFF -> {
                        val newDefensiveMultiplier = charMultipliers.defensive - skillInfo.multiplier

                        val charWithNewDebuff = actualCharInfo.copy(
                            defensiveMultiplier = newDefensiveMultiplier
                        )

                        val damage = getMobSkillDamageUseCase.executeInternal(
                            skillInfo = skillInfo,
                            battleCharInfo = charWithNewDebuff,
                            battleMobInfo = actualMobInfo
                        )

                        val newEnemyHealth = getNewEnemyHealthApplyingDamage(charWithNewDebuff, damage)

                        MobSkillUsageResult.Debuff(
                            newEnemyHealth = newEnemyHealth,
                            repeat = skillInfo.duration,
                            skillId = skillInfo.skillId,
                            skillCategory = skillInfo.skillCategory,
                            refreshTime = skillInfo.refreshTime
                        )
                    }

                    else -> {
                        throw SkillException.SkillCategoryNotHandled()
                    }
                }
            }

            is UsedMobSkillInfo.Heal -> {
                when (skillInfo.skillCategory) {
                    SkillCategory.HEAL -> {
                        val targetMob = liveMobs.minBy { it.actualHealth.toDouble() / it.totalHealth }

                        val newMobHealth = getNewMobHealthApplyingHeal(
                            battleMobInfo = targetMob,
                            heal = skillInfo.lifeRestore
                        )

                        MobSkillUsageResult.Heal(
                            newMobHealth = newMobHealth,
                            refreshTime = skillInfo.refreshTime,
                            targetMobId = targetMob.phaseMobId,
                        )
                    }

                    SkillCategory.AREA_HEAL -> {
                        val newMobsHealth = liveMobs.associate { mob ->
                            val newMobHealth = getNewMobHealthApplyingHeal(
                                battleMobInfo = mob,
                                heal = skillInfo.lifeRestore
                            )

                            mob.phaseMobId to newMobHealth
                        }

                        MobSkillUsageResult.AreaHeal(
                            newMobsHealth = newMobsHealth,
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

    private fun getNewEnemyHealthApplyingDamage(battleCharInfo: BattleCharInfo, damage: Long): Long {
        return max(battleCharInfo.actualHealth - damage, 0)
    }

    private fun getNewMobHealthApplyingHeal(battleMobInfo: BattleMobInfo, heal: Long): Long {
        return min(battleMobInfo.actualHealth + heal, battleMobInfo.totalHealth)
    }
}
