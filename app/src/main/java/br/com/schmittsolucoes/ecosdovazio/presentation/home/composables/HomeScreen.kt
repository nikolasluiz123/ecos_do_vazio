package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun HomeScreen(
    state: HomeUIState = HomeUIState(),
    onDismissErrorDialog: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Home Screen - Sobreviva ao Vazio")

        state.errorMessage?.let { message ->
            ErrorDialog(
                message = message,
                onDismiss = onDismissErrorDialog
            )
        }
    }
}
