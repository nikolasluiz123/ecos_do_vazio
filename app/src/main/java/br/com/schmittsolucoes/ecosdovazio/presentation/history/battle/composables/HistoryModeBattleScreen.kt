package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.CharSection
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.EnemySection
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.SkillsLazyHorizontalGrid
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.SkillsLazyVerticalGrid
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroSlotBackgroundBottom
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroSlotBackgroundTop
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.RoundStrokeColor

internal const val ITEM_ASPECT_RATIO = 0.60f
internal val ITEM_MAX_HEIGHT = 450.dp
internal val SECTION_PADDING_VERTICAL = 12.dp
internal val ITEM_SPACING = 8.dp
internal val SIDE_BY_SIDE_SPACING = 16.dp
internal val ITEM_CORNER_RADIUS = 4.dp
internal val CHAR_AND_MOBS_BORDER_WIDTH = 3.dp
internal val ROUND_BORDER_WIDTH = 2.dp
internal val SKILLS_BORDER_WIDTH = 2.dp
internal val INFO_PADDING = 8.dp

internal const val WEIGHT_SINGLE_MOB = 1f
internal const val WEIGHT_TWO_MOBS = 1.5f
internal const val WEIGHT_THREE_MOBS = 2f
internal const val WEIGHT_MANY_MOBS = 2.5f
internal const val WEIGHT_DEFAULT = 1f

@Composable
fun HistoryModeBattleScreen(
    viewModel: HistoryModeBattleViewModel,
    windowSizeClass: WindowSizeClass,
    onPop: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.actualRound) {
        viewModel.onRoundUpdate()
    }

    LaunchedEffect(state.shouldPop) {
        if (state.shouldPop) {
            onPop()
        }
    }

    HistoryModeBattleScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onMobClick = viewModel::onMobClick,
        onStatusClick = viewModel::onStatusClick,
        onDismissDotTooltip = viewModel::onDismissDotTooltip,
        onSkillClick = viewModel::onSkillClick,
        onSkillLongClick = viewModel::onSkillLongClick,
        onDismissSkillTooltip = viewModel::onDismissSkillTooltip
    )
}

@Composable
fun HistoryModeBattleScreen(
    state: HistoryModeBattleUIState = HistoryModeBattleUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {},
    onMobClick: (BattleMobUIModel) -> Unit = {},
    onStatusClick: (ActiveStatusUIModel) -> Unit = {},
    onDismissDotTooltip: () -> Unit = {},
    onSkillClick: (CharSkillUIModel) -> Unit = {},
    onSkillLongClick: (CharSkillUIModel) -> Unit = {},
    onDismissSkillTooltip: () -> Unit = {}
) {
    val isExpandedWidth = windowSizeClass?.widthSizeClass == WindowWidthSizeClass.Expanded
    val isCompactHeight = windowSizeClass?.heightSizeClass == WindowHeightSizeClass.Compact
    val useSideBySide = isExpandedWidth || isCompactHeight

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient),
            contentAlignment = Alignment.TopCenter
        ) {
            if (useSideBySide) {
                SideBySideLayout(
                    isExpandedWidth = isExpandedWidth,
                    state = state,
                    windowSizeClass = windowSizeClass,
                    paddingValues = paddingValues,
                    onMobClick = onMobClick,
                    onDotClick = onStatusClick,
                    onDismissDotTooltip = onDismissDotTooltip,
                    onSkillClick = onSkillClick,
                    onSkillLongClick = onSkillLongClick,
                    onDismissSkillTooltip = onDismissSkillTooltip
                )
            } else {
                StackLayout(
                    state = state,
                    windowSizeClass = windowSizeClass,
                    paddingValues = paddingValues,
                    onMobClick = onMobClick,
                    onStatusClick = onStatusClick,
                    onDismissDotTooltip = onDismissDotTooltip,
                    onSkillClick = onSkillClick,
                    onSkillLongClick = onSkillLongClick,
                    onDismissSkillTooltip = onDismissSkillTooltip
                )
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
internal fun StackLayout(
    state: HistoryModeBattleUIState,
    windowSizeClass: WindowSizeClass?,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onMobClick: (BattleMobUIModel) -> Unit = {},
    onStatusClick: (ActiveStatusUIModel) -> Unit = {},
    onDismissDotTooltip: () -> Unit = {},
    onSkillClick: (CharSkillUIModel) -> Unit = {},
    onSkillLongClick: (CharSkillUIModel) -> Unit = {},
    onDismissSkillTooltip: () -> Unit = {}
) {
    val layoutDirection = LocalLayoutDirection.current

    Row(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = paddingValues.calculateEndPadding(layoutDirection)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EnemySection(
                state = state,
                onMobClick = onMobClick,
                onStatusClick = onStatusClick,
                onDismissDotTooltip = onDismissDotTooltip,
                windowSizeClass = windowSizeClass,
                modifier = Modifier.weight(1f)
            )

            RoundViewer(
                actualRound = state.actualRound,
                isEnemyRound = state.isEnemyRound
            )

            CharSection(
                char = state.char,
                onStatusClick = onStatusClick,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
        }

        SkillsLazyVerticalGrid(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f),
            onSkillClick = onSkillClick,
            onSkillLongClick = onSkillLongClick,
            onDismissSkillTooltip = onDismissSkillTooltip
        )
    }
}

@Composable
internal fun SideBySideLayout(
    isExpandedWidth: Boolean,
    state: HistoryModeBattleUIState,
    windowSizeClass: WindowSizeClass?,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onMobClick: (BattleMobUIModel) -> Unit = {},
    onDotClick: (ActiveStatusUIModel) -> Unit = {},
    onDismissDotTooltip: () -> Unit = {},
    onSkillClick: (CharSkillUIModel) -> Unit = {},
    onSkillLongClick: (CharSkillUIModel) -> Unit = {},
    onDismissSkillTooltip: () -> Unit = {}
) {
    val layoutDirection = LocalLayoutDirection.current
    val enemyWeight = getEnemyWeight(isExpandedWidth, state)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = SIDE_BY_SIDE_SPACING
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EnemySection(
                state = state,
                onMobClick = onMobClick,
                onStatusClick = onDotClick,
                onDismissDotTooltip = onDismissDotTooltip,
                windowSizeClass = windowSizeClass,
                modifier = Modifier.weight(enemyWeight),
                horizontalArrangement = Arrangement.spacedBy(ITEM_SPACING, Alignment.End)
            )

            Spacer(modifier = Modifier.width(SIDE_BY_SIDE_SPACING))

            RoundViewer(
                actualRound = state.actualRound,
                isEnemyRound = state.isEnemyRound,
            )

            Spacer(modifier = Modifier.width(SIDE_BY_SIDE_SPACING))

            CharSection(
                char = state.char,
                onStatusClick = onDotClick,
                modifier = Modifier.weight(1f),
                alignment = Alignment.CenterStart
            )
        }

        SkillsLazyHorizontalGrid(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2f),
            onSkillClick = onSkillClick,
            onSkillLongClick = onSkillLongClick,
            onDismissSkillTooltip = onDismissSkillTooltip
        )
    }
}

@Composable
private fun RoundViewer(
    actualRound: Long,
    isEnemyRound: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        HeroSlotBackgroundTop,
                        HeroSlotBackgroundBottom
                    )
                ),
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
            .border(
                width = ROUND_BORDER_WIDTH,
                color = RoundStrokeColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(
                R.string.history_mode_battle_screen_label_round,
                actualRound
            ),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold
            )
        )

        val roundSpecificationLabel = if (isEnemyRound) {
            R.string.history_mode_battle_screen_label_enemy_round
        } else {
            R.string.history_mode_battle_screen_label_player_round
        }

        Text(
            text = stringResource(roundSpecificationLabel),
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = FontFamily.Serif
            )
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
                    onMobClick = {},
                    onDotClick = {},
                    onDismissDotTooltip = {},
                    windowSizeClass = null,
                    paddingValues = paddingValues
                )
            }
        }
    }
}

