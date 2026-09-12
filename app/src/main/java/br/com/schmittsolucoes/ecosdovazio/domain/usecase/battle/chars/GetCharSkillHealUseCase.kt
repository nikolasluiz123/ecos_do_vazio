package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateRawHealUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHealAttributePointsUseCase

class GetCharSkillHealUseCase(
    private val getCharHealAttributePointsUseCase: GetCharHealAttributePointsUseCase,
    private val calculateRawHealUseCase: CalculateRawHealUseCase
) {
    fun executeInternal(
        skillInfo: UsedCharSkillInfo.Heal,
        battleCharInfo: BattleCharInfo
    ): Long {
        val healAttributePoints = getCharHealAttributePointsUseCase.executeInternal(
            attributes = battleCharInfo.attributes
        )

        return calculateRawHealUseCase.executeInternal(
            lifeRestore = skillInfo.lifeRestore,
            healAttributePoints = healAttributePoints,
            multiplier = battleCharInfo.offensiveMultiplier
        )
    }
}
