package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier.DEXTERITY
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier.INTELLIGENCE
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier.STRENGTH
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

class GetCharDamageAttributePointsUseCase {
    fun executeInternal(
        attributes: List<IdentifiedCharAttribute>,
        classCategory: ClassCategory
    ): Long {
        return when (classCategory) {
            ClassCategory.WARRIOR -> {
                attributes.find { it.id == STRENGTH }?.attribute?.totalValue!!
            }

            ClassCategory.MAGE -> {
                attributes.find { it.id == INTELLIGENCE }?.attribute?.totalValue!!
            }

            ClassCategory.ARCHER -> {
                attributes.find { it.id == DEXTERITY }?.attribute?.totalValue!!
            }
        }
    }
}