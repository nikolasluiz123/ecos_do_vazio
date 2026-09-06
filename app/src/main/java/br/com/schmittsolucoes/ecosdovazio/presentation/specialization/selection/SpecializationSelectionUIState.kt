package br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection

import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

data class SpecializationSelectionUIState(
    val specializations: List<SelectionItemUIModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedSpecializationId: String? = null,
    val selectedSpecializationSkills: List<CharSkillUIModel>? = null,
    val selectedSpecializationName: String? = null
)
