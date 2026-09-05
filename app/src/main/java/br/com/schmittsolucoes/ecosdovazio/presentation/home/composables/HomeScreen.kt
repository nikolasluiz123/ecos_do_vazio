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
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components.SpecializationBanner
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToSpecializationSelection: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onNavigateToSpecializationSelection = onNavigateToSpecializationSelection
    )
}

@Composable
fun HomeScreen(
    state: HomeUIState = HomeUIState(),
    onDismissErrorDialog: () -> Unit = {},
    onNavigateToSpecializationSelection: () -> Unit = {}
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
                onNavigateToSpecializationSelection = onNavigateToSpecializationSelection
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
    onNavigateToSpecializationSelection: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.showSpecializationBanner) {
            Spacer(modifier = Modifier.height(12.dp))

            SpecializationBanner(
                onClickSelectSpecialization = onNavigateToSpecializationSelection
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
                showSpecializationBanner = true
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
                showSpecializationBanner = true
            )
        )
    }
}
