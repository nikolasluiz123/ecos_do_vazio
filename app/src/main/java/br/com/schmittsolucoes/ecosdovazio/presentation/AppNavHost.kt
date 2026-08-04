package br.com.schmittsolucoes.ecosdovazio.presentation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.charSelectionScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.navigation.classSelectionScreen
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.navigation.navigateToClassSelection

@Composable
fun AppNavHost(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
) {
    NavHost(
        navController = navController,
        startDestination = CharSelectionRoute,
    ) {
        charSelectionScreen(
            windowSizeClass = windowSizeClass,
            onNavigateToClassSelection = navController::navigateToClassSelection
        )

        classSelectionScreen(
            windowSizeClass = windowSizeClass
        )
    }
}
