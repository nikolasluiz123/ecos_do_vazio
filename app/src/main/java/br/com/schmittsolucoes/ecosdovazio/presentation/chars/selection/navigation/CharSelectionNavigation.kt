package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables.CharSelectionScreen

fun NavGraphBuilder.charSelectionScreen(
    windowSizeClass: WindowSizeClass,
    onNavigateToClassSelection: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    composable<CharSelectionRoute> {
        val viewModel = hiltViewModel<CharSelectionViewModel>()

        CharSelectionScreen(
            viewModel = viewModel,
            windowWidthSizeClass = windowSizeClass.widthSizeClass,
            onNavigateToClassSelection = onNavigateToClassSelection,
            onNavigateToHome = onNavigateToHome
        )
    }
}

fun NavController.navigateToCharSelection(navOptions: NavOptions? = null) {
    navigate(route = CharSelectionRoute, navOptions = navOptions)
}
