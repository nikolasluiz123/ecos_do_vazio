package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier.INTELLIGENCE

class GetCharHealAttributePointsUseCase {
    fun executeInternal(
        attributes: List<IdentifiedCharAttribute>
    ): Long {
        return attributes.find { it.id == INTELLIGENCE }?.attribute?.totalValue ?: 0L
    }
}
