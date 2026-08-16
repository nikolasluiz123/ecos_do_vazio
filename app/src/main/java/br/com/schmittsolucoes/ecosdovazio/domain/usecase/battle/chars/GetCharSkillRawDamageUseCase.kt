package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateRawDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharDamageAttributePointsUseCase

class GetCharSkillRawDamageUseCase(
    private val getCharDamageAttributePointsUseCase: GetCharDamageAttributePointsUseCase,
    private val calculateRawDamageUseCase: CalculateRawDamageUseCase
) {
    fun executeInternal(
        classCategory: ClassCategory,
        multiplier: Double,
        attributes: List<IdentifiedCharAttribute>,
        skillDamage: Long
    ): Long {
        val damageAttributePoints = getCharDamageAttributePointsUseCase.executeInternal(
            attributes = attributes,
            classCategory = classCategory
        )

        return calculateRawDamageUseCase.executeInternal(
            skillDamage = skillDamage,
            damageAttributePoints = damageAttributePoints,
            multiplier = multiplier
        )
    }
}