package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection

import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel

data class ClassSelectionUIState(
    val classes: List<ClassSelectionUIModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedClassId: String? = null,
    val charName: String? = null
)
