package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

data class CharAttributes(
    val attributes: List<IdentifiedCharAttribute>,
    val maxAttributeValue: Long
)
