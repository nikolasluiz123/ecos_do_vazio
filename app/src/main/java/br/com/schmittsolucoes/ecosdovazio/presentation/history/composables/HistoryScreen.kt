package br.com.schmittsolucoes.ecosdovazio.presentation.history.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.components.HistoryPhasesGrid
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    windowSizeClass: WindowSizeClass,
    onPhaseClick: (String) -> Unit,
    onInfoClick: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onPhaseClick = onPhaseClick,
        onInfoClick = onInfoClick
    )
}

@Composable
fun HistoryScreen(
    state: HistoryUIState = HistoryUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {},
    onPhaseClick: (String) -> Unit = {},
    onInfoClick: (String) -> Unit = {}
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
        ) {
            HistoryPhasesGrid(
                phases = state.phases,
                actualPhaseIndex = state.actualPhaseIndex,
                windowSizeClass = windowSizeClass,
                onPhaseClick = onPhaseClick,
                onInfoClick = onInfoClick,
                isLoading = state.isLoading,
            )

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Phone - Compact (1 Column) - Light",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Phone - Compact (1 Column) - Dark",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun HistoryScreenPreviewPhone() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HistoryScreen(
            state = HistoryPreviewData.uiStateLoaded,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Foldable - Medium (2 Columns) - Light",
    device = Devices.FOLDABLE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Foldable - Medium (2 Columns) - Dark",
    device = Devices.FOLDABLE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun HistoryScreenPreviewFoldable() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HistoryScreen(
            state = HistoryPreviewData.uiStateLoaded,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Tablet - Expanded (3 Columns) - Light",
    device = Devices.TABLET,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Tablet - Expanded (3 Columns) - Dark",
    device = Devices.TABLET,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun HistoryScreenPreviewTablet() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HistoryScreen(
            state = HistoryPreviewData.uiStateLoaded,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Phone - Error State - Light",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Phone - Error State - Dark",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun HistoryScreenWithErrorPreviewPhone() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HistoryScreen(
            state = HistoryPreviewData.uiStateWithError,
            windowSizeClass = windowSizeClass,
        )
    }
}
