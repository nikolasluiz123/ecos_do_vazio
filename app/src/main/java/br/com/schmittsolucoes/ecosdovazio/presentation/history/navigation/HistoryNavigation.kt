package br.com.schmittsolucoes.ecosdovazio.presentation.history.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.HistoryScreen

fun NavController.navigateToHistory() {
    navigate(HistoryRoute)
}

fun NavGraphBuilder.historyScreen() {
    composable<HistoryRoute> {
        val viewModel = hiltViewModel<HistoryViewModel>()
        HistoryScreen(viewModel = viewModel)
    }
}
