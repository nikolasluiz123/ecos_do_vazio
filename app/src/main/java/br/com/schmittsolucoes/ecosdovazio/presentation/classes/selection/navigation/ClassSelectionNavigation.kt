package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.ClassSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.ClassSelectionScreen

fun NavGraphBuilder.classSelectionScreen(
    windowSizeClass: WindowSizeClass,
    onNavigateToHome: () -> Unit
) {
    composable<ClassSelectionRoute> {
        val viewModel = hiltViewModel<ClassSelectionViewModel>()

        ClassSelectionScreen(
            viewModel = viewModel,
            windowWidthSizeClass = windowSizeClass.widthSizeClass,
            onNavigateToHome = onNavigateToHome
        )
    }
}

fun NavController.navigateToClassSelection(navOptions: NavOptions? = null) {
    navigate(route = ClassSelectionRoute, navOptions = navOptions)
}
