package br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.HomeScreen

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(route = HomeRoute, navOptions = navOptions)
}

fun NavGraphBuilder.homeScreen(
    windowSizeClass: WindowSizeClass,
    onNavigateToSpecializationSelection: () -> Unit = {},
    onNavigateToBattle: (String) -> Unit = {},
    onNavigateToMobsInfo: (String) -> Unit = {}
) {
    composable<HomeRoute> {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(
            viewModel = viewModel,
            windowSizeClass = windowSizeClass,
            onNavigateToSpecializationSelection = onNavigateToSpecializationSelection,
            onNavigateToBattle = onNavigateToBattle,
            onNavigateToMobsInfo = onNavigateToMobsInfo
        )
    }
}
