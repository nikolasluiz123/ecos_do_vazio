package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateEffectiveDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateMobCriticalChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateMobDodgeChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharDamageReductionUseCase

class GetMobSkillDamageUseCase(
    private val getMobSkillRawDamageUseCase: GetMobSkillRawDamageUseCase,
    private val getCharDamageReductionUseCase: GetCharDamageReductionUseCase,
    private val calculateEffectiveDamageUseCase: CalculateEffectiveDamageUseCase,
    private val calculateMobCriticalChanceUseCase: CalculateMobCriticalChanceUseCase,
    private val calculateMobDodgeChanceUseCase: CalculateMobDodgeChanceUseCase
) {
    fun executeInternal(
        skillInfo: UsedMobSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): Long {
        val rawDamage = getMobSkillRawDamageUseCase.executeInternal(
            mobCategory = battleMobInfo.mobCategory,
            mobAttributes = battleMobInfo.attributes,
            level = battleMobInfo.level,
            multiplier = battleMobInfo.offensiveMultiplier,
            skillDamage = skillInfo.damage
        )

        val damageReduction = getCharDamageReductionUseCase.executeInternal(battleCharInfo)

        val criticalChance = calculateMobCriticalChanceUseCase.executeInternal(
            dexterityPoints = battleMobInfo.attributes.dexterity,
            category = battleMobInfo.mobCategory
        )

        val dodgeChance = calculateMobDodgeChanceUseCase.executeInternal(
            agilityPoints = battleMobInfo.attributes.agility,
            category = battleMobInfo.mobCategory
        )

        return calculateEffectiveDamageUseCase.executeInternal(
            rawDamage = rawDamage,
            damageReduction = damageReduction,
            targetMultiplier = battleCharInfo.defensiveMultiplier,
            criticalChance = criticalChance,
            dodgeChance = dodgeChance
        )
    }
}