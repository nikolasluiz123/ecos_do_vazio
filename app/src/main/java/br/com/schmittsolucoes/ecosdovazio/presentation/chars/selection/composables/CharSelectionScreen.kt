package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionViewModel

@Composable
fun CharSelectionScreen(viewModel: CharSelectionViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharSelectionScreen(state)
}

@Composable
fun CharSelectionScreen(state: CharSelectionUIState = CharSelectionUIState()) {

}