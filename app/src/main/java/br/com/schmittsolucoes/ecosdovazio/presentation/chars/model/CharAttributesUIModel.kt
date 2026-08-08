package br.com.schmittsolucoes.ecosdovazio.presentation.chars.model

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes

data class CharAttributesUIModel(
    val identifier: CharAttributes.AttributeIdentifier,
    val totalValue: String,
    val progress: Float
)
