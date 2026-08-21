package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateEffectiveDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobDamageReductionUseCase

class GetCharSkillDamageUseCase(
    private val getCharSkillRawDamageUseCase: GetCharSkillRawDamageUseCase,
    private val getMobDamageReductionUseCase: GetMobDamageReductionUseCase,
    private val calculateEffectiveDamageUseCase: CalculateEffectiveDamageUseCase
) {
    fun executeInternal(
        skillInfo: UsedSkillInfo,
        battleCharInfo: BattleCharInfo,
        battleMobInfo: BattleMobInfo
    ): Long {
        val skillDamage = when (skillInfo) {
            is UsedSkillInfo.CommonDamage -> skillInfo.damage
            is UsedSkillInfo.DamageOverTime -> skillInfo.damage
            else -> 0
        }

        val rawDamage = getCharSkillRawDamageUseCase.executeInternal(
            classCategory = battleCharInfo.classCategory,
            multiplier = battleCharInfo.offensiveMultiplier,
            attributes = battleCharInfo.attributes,
            skillDamage = skillDamage
        )

        val damageReduction = getMobDamageReductionUseCase.executeInternal(battleMobInfo)

        return calculateEffectiveDamageUseCase.executeInternal(
            rawDamage = rawDamage,
            damageReduction = damageReduction,
            targetMultiplier = battleMobInfo.defensiveMultiplier
        )
    }
}
