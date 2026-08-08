package br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.CharScreen

fun NavController.navigateToChar() {
    navigate(CharRoute)
}

fun NavGraphBuilder.charScreen() {
    composable<CharRoute> {
        val viewModel = hiltViewModel<CharViewModel>()
        CharScreen(viewModel = viewModel)
    }
}
