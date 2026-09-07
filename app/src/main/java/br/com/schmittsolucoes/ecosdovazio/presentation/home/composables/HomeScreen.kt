package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components.BannersContainer
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    windowSizeClass: WindowSizeClass,
    onNavigateToSpecializationSelection: () -> Unit = {},
    onNavigateToBattle: (String) -> Unit = {},
    onNavigateToMobsInfo: (String) -> Unit = {},
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onNavigateToSpecializationSelection = onNavigateToSpecializationSelection,
        onNavigateToBattle = onNavigateToBattle,
        onNavigateToMobsInfo = onNavigateToMobsInfo
    )
}

@Composable
fun HomeScreen(
    state: HomeUIState = HomeUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {},
    onNavigateToSpecializationSelection: () -> Unit = {},
    onNavigateToBattle: (String) -> Unit = {},
    onNavigateToMobsInfo: (String) -> Unit = {},
) {
    val scrollState = rememberScrollState()

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
                .verticalScroll(scrollState),
        ) {
            BannersContainer(
                state = state,
                windowSizeClass = windowSizeClass,
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
private fun HomeScreenPreviewPhone() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HomeScreen(
            state = HomePreviewData.homeStateWithAllBanners,
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
private fun HomeScreenPreviewFoldable() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HomeScreen(
            state = HomePreviewData.homeStateCompletedPhase,
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
private fun HomeScreenPreviewTablet() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HomeScreen(
            state = HomePreviewData.homeStateWithAllBanners,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Phone - Only Specialization Banner - Light",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Phone - Only Specialization Banner - Dark",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun HomeScreenPreviewOnlySpecialization() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        HomeScreen(
            state = HomePreviewData.homeStateOnlySpecialization,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Phone - Only History Banner - Light",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true
)
@Preview(
    name = "Phone - Only History Banner - Dark",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun HomeScreenPreviewOnlyHistory() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp)
    )

    EcosDoVazioTheme {
        HomeScreen(
            state = HomePreviewData.homeStateOnlyHistory,
            windowSizeClass = windowSizeClass
        )
    }
}
