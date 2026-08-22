package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateCharCriticalChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateEffectiveDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobDamageReductionUseCase

class GetCharSkillDamageUseCase(
    private val getCharSkillRawDamageUseCase: GetCharSkillRawDamageUseCase,
    private val getMobDamageReductionUseCase: GetMobDamageReductionUseCase,
    private val calculateEffectiveDamageUseCase: CalculateEffectiveDamageUseCase,
    private val calculateCharCriticalChanceUseCase: CalculateCharCriticalChanceUseCase
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

        val dexterityPoints = battleCharInfo.attributes.first {
            it.id == AttributeIdentifier.DEXTERITY
        }.attribute.totalValue

        val criticalChance = calculateCharCriticalChanceUseCase.executeInternal(
            dexterityPoints = dexterityPoints,
            category = battleCharInfo.classCategory
        )

        return calculateEffectiveDamageUseCase.executeInternal(
            rawDamage = rawDamage,
            damageReduction = damageReduction,
            targetMultiplier = battleMobInfo.defensiveMultiplier,
            criticalChance = criticalChance
        )
    }
}
