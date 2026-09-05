package br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection

import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel

data class SpecializationSelectionUIState(
    val specializations: List<SelectionItemUIModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedSpecializationId: String? = null
)
