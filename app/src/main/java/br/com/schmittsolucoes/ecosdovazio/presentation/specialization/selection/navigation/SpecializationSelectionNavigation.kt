package br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection.SpecializationSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection.composables.SpecializationSelectionScreen

fun NavGraphBuilder.specializationSelectionScreen(
    windowSizeClass: WindowSizeClass,
    onNavigateToHome: () -> Unit
) {
    composable<SpecializationSelectionRoute> {
        val viewModel = hiltViewModel<SpecializationSelectionViewModel>()

        SpecializationSelectionScreen(
            viewModel = viewModel,
            windowWidthSizeClass = windowSizeClass.widthSizeClass,
            onNavigateToHome = onNavigateToHome
        )
    }
}

fun NavController.navigateToSpecializationSelection(navOptions: NavOptions? = null) {
    navigate(route = SpecializationSelectionRoute, navOptions = navOptions)
}
