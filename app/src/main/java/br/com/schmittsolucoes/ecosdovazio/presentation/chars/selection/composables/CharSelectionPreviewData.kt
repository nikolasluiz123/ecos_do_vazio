package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel

object CharSelectionPreviewData {

    val warrior = CharSelectionUIModel(
        id = "1",
        name = "Guerreiro",
        presentationImage = R.drawable.classe_guerreiro
    )

    val mage = CharSelectionUIModel(
        id = "2",
        name = "Mago",
        presentationImage = R.drawable.classe_mago
    )

    val archer = CharSelectionUIModel(
        id = "3",
        name = "Arqueiro",
        presentationImage = R.drawable.classe_arqueiro
    )

    val guardian = CharSelectionUIModel(
        id = "4",
        name = "Guardião",
        presentationImage = R.drawable.especializacao_guardiao
    )

    val newHero = CharSelectionUIModel(
        id = null,
        name = null,
        presentationImage = null
    )

    val loadingHero = CharSelectionUIModel(
        isLoading = true
    )

    val charList = listOf(
        warrior,
        mage,
        archer,
        guardian,
        newHero
    )

    val uiStateLoaded = CharSelectionUIState(
        chars = charList
    )

    val uiStateLoading = CharSelectionUIState(
        chars = List(6) { loadingHero }
    )

    val uiStateWithError = CharSelectionUIState(
        chars = charList,
        errorMessage = "Erro ao carregar dados do usuário."
    )
}
