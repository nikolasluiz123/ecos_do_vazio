package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier

data class CharAttributes(
    val attributes: List<Attributes>,
    val maxAttributeValue: Long
) {
    data class Attributes(
        val id: AttributeIdentifier,
        val charValue: Long,
        val classValue: Long,
        val specializationValue: Long,
    )
}
