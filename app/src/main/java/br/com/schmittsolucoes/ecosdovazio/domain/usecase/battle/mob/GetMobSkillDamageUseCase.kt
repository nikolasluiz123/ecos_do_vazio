package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateEffectiveDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharDamageReductionUseCase

class GetMobSkillDamageUseCase(
    private val getMobSkillRawDamageUseCase: GetMobSkillRawDamageUseCase,
    private val getCharDamageReductionUseCase: GetCharDamageReductionUseCase,
    private val calculateEffectiveDamageUseCase: CalculateEffectiveDamageUseCase
) {
    fun executeInternal(
        skillInfo: UsedMobSkillInfo.CommonDamage,
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

        return calculateEffectiveDamageUseCase.executeInternal(
            rawDamage = rawDamage,
            damageReduction = damageReduction,
            targetMultiplier = battleCharInfo.defensiveMultiplier
        )
    }
}