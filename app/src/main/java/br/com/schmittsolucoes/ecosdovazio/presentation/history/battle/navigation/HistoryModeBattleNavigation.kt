package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.HistoryModeBattleScreen

fun NavController.navigateToHistoryModeBattle(phaseId: String) {
    navigate(HistoryModeBattleRoute(phaseId))
}

fun NavGraphBuilder.historyModeBattleScreen(
    windowSizeClass: WindowSizeClass,
    onPop: () -> Unit
) {
    composable<HistoryModeBattleRoute> {
        val viewModel = hiltViewModel<HistoryModeBattleViewModel>()
        HistoryModeBattleScreen(
            viewModel = viewModel,
            windowSizeClass = windowSizeClass,
            onPop = onPop
        )
    }
}
