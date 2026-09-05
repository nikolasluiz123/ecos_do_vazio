package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.HistoryMobsInfoViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables.HistoryMobsInfoScreen

fun NavController.navigateToHistoryMobsInfo(phaseId: String) {
    navigate(HistoryMobsInfoRoute(phaseId))
}

fun NavGraphBuilder.historyMobsInfoScreen(
    windowSizeClass: WindowSizeClass,
) {
    composable<HistoryMobsInfoRoute> {
        val viewModel = hiltViewModel<HistoryMobsInfoViewModel>()
        HistoryMobsInfoScreen(
            viewModel = viewModel,
            windowSizeClass = windowSizeClass,
        )
    }
}
