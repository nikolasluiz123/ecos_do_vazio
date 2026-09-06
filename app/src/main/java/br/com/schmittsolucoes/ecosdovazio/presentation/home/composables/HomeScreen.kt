package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.model.LastUnfinishedHistoryPhaseUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components.HistoryBanner
import br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components.SpecializationBanner
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSpecializationSelection: () -> Unit = {},
    onNavigateToBattle: (String) -> Unit = {},
    onNavigateToMobsInfo: (String) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onNavigateToSpecializationSelection = onNavigateToSpecializationSelection,
        onNavigateToBattle = onNavigateToBattle,
        onNavigateToMobsInfo = onNavigateToMobsInfo
    )
}

@Composable
fun HomeScreen(
    state: HomeUIState = HomeUIState(),
    onDismissErrorDialog: () -> Unit = {},
    onNavigateToSpecializationSelection: () -> Unit = {},
    onNavigateToBattle: (String) -> Unit = {},
    onNavigateToMobsInfo: (String) -> Unit = {}
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient),
            contentAlignment = Alignment.Center
        ) {
            BannersContainer(
                state = state,
                onNavigateToSpecializationSelection = onNavigateToSpecializationSelection,
                onNavigateToBattle = onNavigateToBattle,
                onNavigateToMobsInfo = onNavigateToMobsInfo
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

@Composable
private fun BannersContainer(
    state: HomeUIState,
    onNavigateToSpecializationSelection: () -> Unit,
    onNavigateToBattle: (String) -> Unit,
    onNavigateToMobsInfo: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.showSpecializationBanner) {
            SpecializationBanner(
                onClickSelectSpecialization = onNavigateToSpecializationSelection
            )
        }

        state.lastUnfinishedHistoryPhase?.let { lastUnfinishedHistoryPhase ->
            Spacer(modifier = Modifier.height(12.dp))

            HistoryBanner(
                model = lastUnfinishedHistoryPhase,
                onNavigateToBattle = onNavigateToBattle,
                onNavigateToMobsInfo = onNavigateToMobsInfo
            )
        }
    }
}

@Preview(name = "Light Mode - Specialization Banner", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        HomeScreen(
            state = HomeUIState(
                showSpecializationBanner = true,
                lastUnfinishedHistoryPhase = LastUnfinishedHistoryPhaseUIModel(
                    phaseId = "1",
                    phaseName = "Espada Lascada",
                    completedPhasesCount = 2,
                    totalPhasesCount = 10,
                    progress = 0.2f,
                    isCompleted = false
                )
            )
        )
    }
}

@Preview(name = "Dark Mode - Specialization Banner", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        HomeScreen(
            state = HomeUIState(
                showSpecializationBanner = true,
                lastUnfinishedHistoryPhase = LastUnfinishedHistoryPhaseUIModel(
                    phaseId = null,
                    phaseName = "",
                    completedPhasesCount = 10,
                    totalPhasesCount = 10,
                    progress = 1f,
                    isCompleted = true
                )
            )
        )
    }
}
