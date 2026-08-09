package br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.CharScreen

fun NavController.navigateToChar() {
    navigate(CharRoute)
}

fun NavGraphBuilder.charScreen(
    windowSizeClass: WindowSizeClass
) {
    composable<CharRoute> {
        val viewModel = hiltViewModel<CharViewModel>()
        CharScreen(
            viewModel = viewModel,
            windowSizeClass = windowSizeClass
        )
    }
}
