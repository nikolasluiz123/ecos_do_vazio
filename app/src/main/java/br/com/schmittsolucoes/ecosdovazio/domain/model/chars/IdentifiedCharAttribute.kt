package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier

data class IdentifiedCharAttribute(
    val id: AttributeIdentifier,
    val attribute: CharAttribute,
)
