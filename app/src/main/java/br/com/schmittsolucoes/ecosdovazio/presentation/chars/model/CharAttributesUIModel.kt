package br.com.schmittsolucoes.ecosdovazio.presentation.chars.model

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier

data class CharAttributesUIModel(
    val identifier: AttributeIdentifier,
    val totalValue: String,
    val progress: Float,
    val canIncrement: Boolean,
    val canDecrement: Boolean
)
