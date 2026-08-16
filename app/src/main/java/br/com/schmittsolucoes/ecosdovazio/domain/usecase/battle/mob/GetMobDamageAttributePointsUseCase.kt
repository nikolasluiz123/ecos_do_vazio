package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob

class GetMobDamageAttributePointsUseCase(
    private val getMobAttributesByLevelUseCase: GetMobAttributesByLevelUseCase
) {
    fun executeInternal(
        mobCategory: MobCategory,
        mobAttributes: Mob.Attributes,
        level: Long
    ): Long {
        val attributes = getMobAttributesByLevelUseCase.executeInternal(
            level = level,
            mobCategory = mobCategory,
            attributes = mobAttributes
        )

        return when (mobCategory) {
            MobCategory.WARRIOR, MobCategory.ORC_WARRIOR -> attributes.strength
            MobCategory.MAGE, MobCategory.HEALER -> attributes.intelligence
        }
    }
}