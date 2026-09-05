package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection

import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel

data class ClassSelectionUIState(
    val classes: List<SelectionItemUIModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedClassId: String? = null,
    val charName: String? = null
)
