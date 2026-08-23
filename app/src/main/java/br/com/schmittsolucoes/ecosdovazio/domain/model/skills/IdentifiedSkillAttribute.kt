package br.com.schmittsolucoes.ecosdovazio.domain.model.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier

data class IdentifiedSkillAttribute(
    val id: AttributeIdentifier,
    val attribute: Long,
)
