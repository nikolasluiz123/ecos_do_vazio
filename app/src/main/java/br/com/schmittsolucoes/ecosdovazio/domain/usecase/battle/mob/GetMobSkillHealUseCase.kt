package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateRawHealUseCase

class GetMobSkillHealUseCase(
    private val getMobHealAttributePointsUseCase: GetMobHealAttributePointsUseCase,
    private val calculateRawHealUseCase: CalculateRawHealUseCase
) {
    fun executeInternal(
        mobCategory: MobCategory,
        mobAttributes: Mob.Attributes,
        level: Long,
        multiplier: Double,
        lifeRestore: Long
    ): Long {
        val healAttributePoints = getMobHealAttributePointsUseCase.executeInternal(
            mobCategory = mobCategory,
            mobAttributes = mobAttributes,
            level = level
        )

        return calculateRawHealUseCase.executeInternal(
            lifeRestore = lifeRestore,
            healAttributePoints = healAttributePoints,
            multiplier = multiplier
        )
    }
}
