package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import kotlin.math.roundToLong

class GetCharSkillDamageUseCase(
    private val getCharDamageAttributePointsUseCase: GetCharDamageAttributePointsUseCase,
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

        return (skillDamage + (damageAttributePoints * multiplier)).roundToLong()
    }
}