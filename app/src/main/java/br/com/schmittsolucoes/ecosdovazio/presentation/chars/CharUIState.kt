package br.com.schmittsolucoes.ecosdovazio.presentation.chars

import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharLevelInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharStatusUIModel

data class CharUIState(
    val levelInfo: CharLevelInfoUIModel? = null,
    val statusInfo: CharStatusUIModel? = null,
    val attributesInfo: List<CharAttributesUIModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
