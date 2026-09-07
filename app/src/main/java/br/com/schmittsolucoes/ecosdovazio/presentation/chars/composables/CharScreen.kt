package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components.CharStatus
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun CharScreen(
    viewModel: CharViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onIncrementAttribute = viewModel::onIncrementAttribute,
        onDecrementAttribute = viewModel::onDecrementAttribute,
    )
}

@Composable
fun CharScreen(
    state: CharUIState = CharUIState(),
    windowSizeClass: WindowSizeClass,
    onDismissErrorDialog: () -> Unit = {},
    onIncrementAttribute: (AttributeIdentifier) -> Unit = {},
    onDecrementAttribute: (AttributeIdentifier) -> Unit = {},
) {
    Scaffold { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
                .verticalScroll(scrollState)
                .padding(16.dp),
        ) {

            state.levelInfo?.let { levelInfo ->
                CharLevelInfo(levelInfo = levelInfo)
            }

            Spacer(modifier = Modifier.height(24.dp))

            state.statusInfo?.let { statusInfo ->
                CharStatus(
                    statusInfo = statusInfo,
                    windowSizeClass = windowSizeClass,
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            state.attributesInfo?.let { attributesInfo ->
                CharAttributes(
                    attributes = attributesInfo,
                    availablePoints = state.availablePoints,
                    windowSizeClass = windowSizeClass,
                    onIncrementAttribute = onIncrementAttribute,
                    onDecrementAttribute = onDecrementAttribute,
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
    name = "Light Mode",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Dark Mode",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun CharScreenPreview() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharScreen(
            state = CharPreviewData.uiStateLoaded,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Error - Light Mode",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Error - Dark Mode",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
private fun CharScreenWithErrorPreview() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharScreen(
            state = CharPreviewData.uiStateWithError,
            windowSizeClass = windowSizeClass,
        )
    }
}
