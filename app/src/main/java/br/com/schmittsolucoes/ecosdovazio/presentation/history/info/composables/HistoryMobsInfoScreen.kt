package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.HistoryMobsInfoUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.HistoryMobsInfoViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables.components.HistoryPhaseMobsList
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables.components.PhaseInfoHeader
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseDataUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun HistoryMobsInfoScreen(
    viewModel: HistoryMobsInfoViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryMobsInfoScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
    )
}

@Composable
fun HistoryMobsInfoScreen(
    state: HistoryMobsInfoUIState = HistoryMobsInfoUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {},
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                state.phase?.name?.takeIf { it.isNotBlank() }?.let { phaseName ->
                    PhaseInfoHeader(phaseName = phaseName)
                }

                HistoryPhaseMobsList(
                    mobsInfo = state.mobsInfo,
                    windowSizeClass = windowSizeClass,
                )
            }

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Phone - Compact (1 Column) Light",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Phone - Compact (1 Column) Dark",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun HistoryMobsInfoScreenPreviewCompact() {
    EcosDoVazioTheme {
        HistoryMobsInfoScreen(
            state = HistoryMobsInfoUIState(
                phaseId = "1",
                phase = HistoryPhaseDataUIModel(name = "O Primeiro Guarda"),
                mobsInfo = HistoryMobsInfoPreviewData.fewMobsPhaseInfo,
            ),
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(360.dp, 800.dp)),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Foldable - Medium (2 Columns)",
    device = Devices.FOLDABLE,
    showBackground = true,
)
@Composable
private fun HistoryMobsInfoScreenPreviewMedium() {
    EcosDoVazioTheme {
        HistoryMobsInfoScreen(
            state = HistoryMobsInfoUIState(
                phaseId = "15",
                phase = HistoryPhaseDataUIModel(name = "Batalhão Completo"),
                mobsInfo = HistoryMobsInfoPreviewData.manyMobsPhaseInfo,
            ),
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(700.dp, 800.dp)),
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Tablet - Expanded (3 Columns)",
    device = Devices.TABLET,
    showBackground = true,
)
@Composable
private fun HistoryMobsInfoScreenPreviewExpanded() {
    EcosDoVazioTheme {
        HistoryMobsInfoScreen(
            state = HistoryMobsInfoUIState(
                phaseId = "15",
                phase = HistoryPhaseDataUIModel(name = "Batalhão Completo"),
                mobsInfo = HistoryMobsInfoPreviewData.manyMobsPhaseInfo,
            ),
            windowSizeClass = WindowSizeClass.calculateFromSize(DpSize(1280.dp, 800.dp)),
        )
    }
}
