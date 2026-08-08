package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient

@Composable
fun CharScreen(
    viewModel: CharViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun CharScreen(
    state: CharUIState = CharUIState(),
    onDismissErrorDialog: () -> Unit = {}
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Character Screen")

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog
                )
            }
        }
    }
}
