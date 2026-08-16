package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateRawDamageUseCase

class GetMobSkillRawDamageUseCase(
    private val getMobDamageAttributePointsUseCase: GetMobDamageAttributePointsUseCase,
    private val calculateRawDamageUseCase: CalculateRawDamageUseCase
) {
    fun executeInternal(
        mobCategory: MobCategory,
        mobAttributes: Mob.Attributes,
        level: Long,
        multiplier: Double,
        skillDamage: Long
    ): Long {
        val damageAttributePoints = getMobDamageAttributePointsUseCase.executeInternal(
            mobCategory = mobCategory,
            mobAttributes = mobAttributes,
            level = level
        )

        return calculateRawDamageUseCase.executeInternal(
            skillDamage = skillDamage,
            damageAttributePoints = damageAttributePoints,
            multiplier = multiplier
        )
    }
}