package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.CharSection
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.EnemySection
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.SkillsLazyHorizontalGrid
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.SkillsLazyVerticalGrid
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

internal const val ITEM_ASPECT_RATIO = 0.60f
internal val ITEM_MAX_HEIGHT = 450.dp
internal val SECTION_PADDING_VERTICAL = 12.dp
internal val ITEM_SPACING = 8.dp
internal val SIDE_BY_SIDE_SPACING = 0.dp
internal val ITEM_CORNER_RADIUS = 4.dp
internal val ITEM_BORDER_WIDTH = 2.dp
internal val INFO_PADDING = 8.dp

internal const val WEIGHT_SINGLE_MOB = 1f
internal const val WEIGHT_TWO_MOBS = 1.5f
internal const val WEIGHT_THREE_MOBS = 2f
internal const val WEIGHT_MANY_MOBS = 2.5f
internal const val WEIGHT_DEFAULT = 1f

@Composable
fun HistoryModeBattleScreen(
    viewModel: HistoryModeBattleViewModel,
    windowSizeClass: WindowSizeClass
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryModeBattleScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun HistoryModeBattleScreen(
    state: HistoryModeBattleUIState = HistoryModeBattleUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {}
) {
    val isExpandedWidth = windowSizeClass?.widthSizeClass == WindowWidthSizeClass.Expanded
    val isCompactHeight = windowSizeClass?.heightSizeClass == WindowHeightSizeClass.Compact
    val useSideBySide = isExpandedWidth || isCompactHeight

    Scaffold { paddingValues ->
        val layoutDirection = LocalLayoutDirection.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = if (useSideBySide) SIDE_BY_SIDE_SPACING else paddingValues.calculateEndPadding(layoutDirection)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            if (useSideBySide) {
                SideBySideLayout(isExpandedWidth, state, windowSizeClass)
            } else {
                StackLayout(state, windowSizeClass)
            }

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
internal fun StackLayout(state: HistoryModeBattleUIState, windowSizeClass: WindowSizeClass?) {
    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().weight(0.7f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EnemySection(
                mobs = state.mobs,
                windowSizeClass = windowSizeClass,
                modifier = Modifier.weight(1f)
            )

            CharSection(
                char = state.char,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        SkillsLazyVerticalGrid(
            state = state,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.fillMaxSize().weight(0.3f)
        )
    }
}

@Composable
internal fun SideBySideLayout(
    isExpandedWidth: Boolean,
    state: HistoryModeBattleUIState,
    windowSizeClass: WindowSizeClass?
) {
    val enemyWeight = getEnemyWeight(isExpandedWidth, state)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.7f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EnemySection(
                mobs = state.mobs,
                windowSizeClass = windowSizeClass,
                modifier = Modifier.weight(enemyWeight),
                horizontalArrangement = Arrangement.spacedBy(ITEM_SPACING, Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.width(SIDE_BY_SIDE_SPACING))

            CharSection(
                char = state.char,
                modifier = Modifier.weight(1f),
                alignment = Alignment.Center
            )
        }

        SkillsLazyHorizontalGrid(
            state = state,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.fillMaxSize().weight(0.3f)
        )
    }
}

private fun getEnemyWeight(
    isExpandedWidth: Boolean,
    state: HistoryModeBattleUIState
): Float {
    return if (isExpandedWidth) {
        when (state.mobs.size) {
            1 -> WEIGHT_SINGLE_MOB
            2 -> WEIGHT_TWO_MOBS
            3 -> WEIGHT_THREE_MOBS
            else -> WEIGHT_MANY_MOBS
        }
    } else {
        WEIGHT_DEFAULT
    }
}

@Preview(showBackground = true, device = Devices.PHONE)
@Preview(showBackground = true, device = Devices.PHONE, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HistoryModeBattleScreenStackPreview() {
    EcosDoVazioTheme {
        HistoryModeBattleScreen(
            state = HistoryModeBattlePreviewData.uiState
        )
    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,orientation=landscape")
@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,orientation=landscape", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HistoryModeBattleScreenSideBySidePreview() {
    EcosDoVazioTheme {
        Scaffold { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundGradient)
                    .padding(paddingValues)
            ) {
                SideBySideLayout(
                    isExpandedWidth = true,
                    state = HistoryModeBattlePreviewData.uiState,
                    windowSizeClass = null
                )
            }
        }
    }
}

