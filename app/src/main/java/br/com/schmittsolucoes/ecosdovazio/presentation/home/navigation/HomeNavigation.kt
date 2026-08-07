package br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.HomeScreen

fun NavController.navigateToHome() {
    navigate(HomeRoute)
}

fun NavGraphBuilder.homeScreen() {
    composable<HomeRoute> {
        val viewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(viewModel = viewModel)
    }
}
