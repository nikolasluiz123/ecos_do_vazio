package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel

fun ClassSelection.toUIModel(presentationDrawableId: Int): ClassSelectionUIModel {
    return ClassSelectionUIModel(
        id = id,
        name = name,
        description = description,
        presentationDrawableId = presentationDrawableId
    )
}