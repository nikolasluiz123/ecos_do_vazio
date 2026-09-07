package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.ClassSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel

object ClassSelectionPreviewData {

    val warrior = SelectionItemUIModel(
        id = "1",
        name = "Guerreiro",
        description = "Especialista em combate corpo a corpo, atua na linha de frente equipado com armaduras pesadas.",
        presentationDrawableId = R.drawable.classe_guerreiro,
    )

    val mage = SelectionItemUIModel(
        id = "2",
        name = "Mago",
        description = "Mestre em feitiços e ataques à distância, veste armaduras leves de tecido.",
        presentationDrawableId = R.drawable.classe_mago,
    )

    val archer = SelectionItemUIModel(
        id = "3",
        name = "Arqueiro",
        description = "Mestre no uso de arcos e agilidade, ataca à distância com extrema precisão.",
        presentationDrawableId = R.drawable.classe_arqueiro,
    )

    val classesList = listOf(
        warrior,
        mage,
        archer,
    )

    val uiStateLoaded = ClassSelectionUIState(
        classes = classesList,
    )

    val uiStateWithError = ClassSelectionUIState(
        classes = classesList,
        errorMessage = "Erro ao selecionar classe.",
    )
}
