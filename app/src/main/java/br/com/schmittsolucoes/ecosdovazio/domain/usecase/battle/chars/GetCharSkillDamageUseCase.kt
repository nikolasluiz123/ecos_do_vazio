package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateCharCriticalChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateCharDodgeChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateEffectiveDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobDamageReductionUseCase

class GetCharSkillDamageUseCase(
    private val getCharSkillRawDamageUseCase: GetCharSkillRawDamageUseCase,
    private val getMobDamageReductionUseCase: GetMobDamageReductionUseCase,
    private val calculateEffectiveDamageUseCase: CalculateEffectiveDamageUseCase,
    private val calculateCharCriticalChanceUseCase: CalculateCharCriticalChanceUseCase,
    private val calculateCharDodgeChanceUseCase: CalculateCharDodgeChanceUseCase
) {
    fun executeInternal(
        skillInfo: UsedCharSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): Long {
        val rawDamage = getCharSkillRawDamageUseCase.executeInternal(
            classCategory = battleCharInfo.classCategory,
            multiplier = battleCharInfo.offensiveMultiplier,
            attributes = battleCharInfo.attributes,
            skillDamage = skillInfo.damage
        )

        val damageReduction = getMobDamageReductionUseCase.executeInternal(battleMobInfo)

        val criticalChance = calculateCharCriticalChanceUseCase.executeInternal(
            dexterityPoints = battleCharInfo.getAttribute(AttributeIdentifier.DEXTERITY),
            category = battleCharInfo.classCategory
        )

        val dodgeChance = calculateCharDodgeChanceUseCase.executeInternal(
            agilityPoints = battleCharInfo.getAttribute(AttributeIdentifier.AGILITY),
            category = battleCharInfo.classCategory
        )

        return calculateEffectiveDamageUseCase.executeInternal(
            rawDamage = rawDamage,
            damageReduction = damageReduction,
            targetMultiplier = battleMobInfo.defensiveMultiplier,
            criticalChance = criticalChance,
            dodgeChance = dodgeChance
        )
    }

    private fun BattleCharInfo.getAttribute(identifier: AttributeIdentifier): Long {
        return attributes.first { it.id == identifier }.attribute.totalValue
    }
}
