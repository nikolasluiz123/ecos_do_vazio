package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection

import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

data class ClassSelectionUIState(
    val classes: List<SelectionItemUIModel> = emptyList(),
    val errorMessage: String? = null,
    val selectedClassId: String? = null,
    val charName: String? = null,
    val selectedClassSkills: List<CharSkillUIModel>? = null,
    val selectedClassName: String? = null
)
