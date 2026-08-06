package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection

import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel

data class CharSelectionUIState(
    val errorMessage: String? = null,
    val chars: List<CharSelectionUIModel> = List(6) { CharSelectionUIModel(isLoading = true) }
)
