package br.com.schmittsolucoes.ecosdovazio.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.navigation.charScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.charSelectionScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.navigation.classSelectionScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.navigation.navigateToClassSelection
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation.historyModeBattleScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation.navigateToHistoryModeBattle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.navigation.historyScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.HomeRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.homeScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.home.navigation.navigateToHome

@Composable
fun AppNavHost(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    startDestination: Any = CharSelectionRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        charSelectionScreen(
            windowSizeClass = windowSizeClass,
            onNavigateToClassSelection = navController::navigateToClassSelection,
            onNavigateToHome = navController::navigateToHome
        )

        classSelectionScreen(
            windowSizeClass = windowSizeClass,
            onNavigateToHome = navController::navigateToHome
        )

        historyModeBattleScreen(windowSizeClass = windowSizeClass)

        navigation<MainGraph>(
            startDestination = HomeRoute
        ) {
            homeScreen()
            charScreen(windowSizeClass = windowSizeClass)
            historyScreen(
                onNavigateToBattle = navController::navigateToHistoryModeBattle
            )
        }
    }
}
