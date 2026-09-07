package br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables

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
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.CharSkillsUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.CharSkillsViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables.components.CharSkillsGrid
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun CharSkillsScreen(
    viewModel: CharSkillsViewModel,
    windowSizeClass: WindowSizeClass,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharSkillsScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onSelectSkill = viewModel::onSelectSkill,
        onDismissSkillDetails = viewModel::onDismissSkillDetails,
        onIncrementAttribute = viewModel::onIncrementAttribute,
        onDecrementAttribute = viewModel::onDecrementAttribute,
    )
}

@Composable
fun CharSkillsScreen(
    state: CharSkillsUIState = CharSkillsUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {},
    onSelectSkill: (CharSkillDetailsUIModel) -> Unit = {},
    onDismissSkillDetails: () -> Unit = {},
    onIncrementAttribute: (AttributeIdentifier) -> Unit = {},
    onDecrementAttribute: (AttributeIdentifier) -> Unit = {},
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient),
        ) {
            CharSkillsGrid(
                skills = state.skills,
                windowSizeClass = windowSizeClass,
                onSelectSkill = onSelectSkill,
            )

            state.selectedSkill?.let { skill ->
                CharSkillDetailsBottomSheet(
                    skill = skill,
                    attributes = state.selectedSkillAttributes,
                    availablePoints = state.availablePoints,
                    onDismissRequest = onDismissSkillDetails,
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
    name = "Phone - Compact (2 Columns) - Light",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Phone - Compact (2 Columns) - Dark",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun CharSkillsScreenPreviewPhone() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharSkillsScreen(
            state = CharSkillsPreviewData.uiStateLoaded,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Foldable - Medium (3 Columns) - Light",
    device = Devices.FOLDABLE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Foldable - Medium (3 Columns) - Dark",
    device = Devices.FOLDABLE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun CharSkillsScreenPreviewFoldable() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharSkillsScreen(
            state = CharSkillsPreviewData.uiStateLoaded,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Tablet - Expanded (5 Columns) - Light",
    device = Devices.TABLET,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Tablet - Expanded (5 Columns) - Dark",
    device = Devices.TABLET,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun CharSkillsScreenPreviewTablet() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharSkillsScreen(
            state = CharSkillsPreviewData.uiStateLoaded,
            windowSizeClass = windowSizeClass,
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Phone - Selected Skill - Light",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Phone - Selected Skill - Dark",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun CharSkillsScreenWithSelectedSkillPreviewPhone() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharSkillsScreen(
            state = CharSkillsPreviewData.uiStateWithSelectedSkill,
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
private fun CharSkillsScreenWithErrorPreviewPhone() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharSkillsScreen(
            state = CharSkillsPreviewData.uiStateWithError,
            windowSizeClass = windowSizeClass,
        )
    }
}
