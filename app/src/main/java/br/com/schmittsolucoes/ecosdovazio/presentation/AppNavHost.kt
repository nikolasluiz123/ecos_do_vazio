package br.com.schmittsolucoes.ecosdovazio.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.charSelectionScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = CharSelectionRoute,
        modifier = modifier
    ) {
        charSelectionScreen()
    }
}
