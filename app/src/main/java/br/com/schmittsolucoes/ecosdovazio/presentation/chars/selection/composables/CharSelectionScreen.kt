package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionNavigationEvent
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables.components.HeroesGridList
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ObserveAsEvents
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun CharSelectionScreen(
    viewModel: CharSelectionViewModel,
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToClassSelection: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.navigationEvent) { event ->
        when (event) {
            CharSelectionNavigationEvent.NavigateToHome -> onNavigateToHome()
        }
    }

    CharSelectionScreen(
        state = state,
        windowWidthSizeClass = windowWidthSizeClass,
        onNavigateToClassSelection = onNavigateToClassSelection,
        onCharSelected = viewModel::onCharSelected,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
    )
}

@Composable
fun CharSelectionScreen(
    state: CharSelectionUIState = CharSelectionUIState(),
    windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onNavigateToClassSelection: () -> Unit = {},
    onCharSelected: (String) -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
                .padding(paddingValues),
        ) {
            HeroesGridList(
                chars = state.chars,
                windowWidthSizeClass = windowWidthSizeClass,
                onNavigateToClassSelection = onNavigateToClassSelection,
                onCharSelected = onCharSelected,
            )

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog,
                )
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun CharSelectionScreenPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        CharSelectionScreen(
            state = CharSelectionPreviewData.uiStateLoaded,
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CharSelectionScreenPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        CharSelectionScreen(
            state = CharSelectionPreviewData.uiStateLoaded,
        )
    }
}
