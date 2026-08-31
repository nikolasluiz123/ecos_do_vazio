package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.CHAR_AND_MOBS_BORDER_WIDTH
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.HistoryModeBattlePreviewData
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.INFO_PADDING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_ASPECT_RATIO
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_CORNER_RADIUS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_MAX_HEIGHT
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_SPACING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.SECTION_PADDING_VERTICAL
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.status.ActiveStatusTooltip
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getLevelStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getNameStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.CharacterBattleStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightOnImage
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.NegativeStatus
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OnSurfaceVariantOnImage
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.PositiveStatus

private const val PULSE_ANIMATION_DURATION = 600
private const val PULSE_ALPHA_INITIAL = 0.4f
private const val PULSE_ALPHA_TARGET = 1f

@Composable
internal fun EnemySection(
    state: HistoryModeBattleUIState,
    onMobClick: (BattleMobUIModel) -> Unit,
    onStatusClick: (ActiveStatusUIModel) -> Unit,
    onDismissDotTooltip: () -> Unit,
    windowSizeClass: WindowSizeClass?,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(ITEM_SPACING)
) {
    if (state.mobs.isEmpty()) return

    val isExpanded = windowSizeClass?.widthSizeClass == WindowWidthSizeClass.Expanded

    if (isExpanded) {
        EnemyHorizontalList(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            mobs = state.mobs,
            selectedMob = state.selectedMob,
            onMobClick = onMobClick,
            onStatusClick = onStatusClick
        )
    } else {
        EnemyHorizontalPager(
            mobs = state.mobs,
            selectedMob = state.selectedMob,
            onMobClick = onMobClick,
            onStatusClick = onStatusClick,
            modifier = modifier
        )
    }

    state.selectedActiveStatus?.let { dot ->
        ActiveStatusTooltip(
            status = dot,
            onDismissRequest = onDismissDotTooltip
        )
    }
}

@Composable
private fun EnemyHorizontalList(
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal,
    mobs: List<BattleMobUIModel>,
    selectedMob: BattleMobUIModel?,
    onMobClick: (BattleMobUIModel) -> Unit,
    onStatusClick: (ActiveStatusUIModel) -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        items(mobs) { mob ->
            EnemyItem(
                mob = mob,
                isSelected = mob.phaseMobId == selectedMob?.phaseMobId,
                onMobClick = onMobClick,
                onDotClick = onStatusClick,
                modifier = Modifier
                    .heightIn(max = ITEM_MAX_HEIGHT)
                    .fillMaxHeight()
                    .padding(vertical = SECTION_PADDING_VERTICAL)
            )
        }
    }
}

@Composable
private fun EnemyHorizontalPager(
    mobs: List<BattleMobUIModel>,
    selectedMob: BattleMobUIModel?,
    onMobClick: (BattleMobUIModel) -> Unit,
    onStatusClick: (ActiveStatusUIModel) -> Unit,
    modifier: Modifier
) {
    val pagerState = rememberPagerState { mobs.size }

    LaunchedEffect(selectedMob) {
        selectedMob?.let { mob ->
            val index = mobs.indexOfFirst { it.phaseMobId == mob.phaseMobId }

            if (index != -1 && index != pagerState.currentPage) {
                pagerState.animateScrollToPage(index, animationSpec = spring(stiffness = Spring.StiffnessVeryLow))
            }
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
        ) { page ->
            val mob = mobs[page]
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EnemyItem(
                    mob = mob,
                    isSelected = mob.phaseMobId == selectedMob?.phaseMobId,
                    onMobClick = onMobClick,
                    onDotClick = onStatusClick,
                    modifier = Modifier
                        .heightIn(max = ITEM_MAX_HEIGHT)
                        .fillMaxHeight()
                        .padding(vertical = SECTION_PADDING_VERTICAL)
                )
            }
        }

        PulsingArrow(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            isVisible = pagerState.canScrollBackward,
            modifier = Modifier
                .align(Alignment.CenterStart)
        )

        PulsingArrow(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            isVisible = pagerState.canScrollForward,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun EnemyItem(
    mob: BattleMobUIModel,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    onMobClick: (BattleMobUIModel) -> Unit = {},
    onDotClick: (ActiveStatusUIModel) -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = PULSE_ALPHA_INITIAL,
        targetValue = PULSE_ALPHA_TARGET,
        animationSpec = infiniteRepeatable(
            animation = tween(PULSE_ANIMATION_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Alpha"
    )

    val borderColor = if (isSelected) {
        OrangeForDetails.copy(alpha = alpha)
    } else {
        CharacterBattleStrokeColor
    }

    Row(
        modifier = modifier
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .aspectRatio(ITEM_ASPECT_RATIO)
                .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
                .border(
                    width = CHAR_AND_MOBS_BORDER_WIDTH,
                    color = borderColor,
                    shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
                )
                .padding(CHAR_AND_MOBS_BORDER_WIDTH)
                .clickable { onMobClick(mob) }
        ) {
            BattleAsyncImage(
                model = mob.image,
                contentDescription = mob.name,
                modifier = Modifier.fillMaxSize()
            )

            AppliedStatus(mob.activeStatus, isPlayerStatus = false, onDotClick)

            EnemyInfo(mob, maxWidth)
        }
    }
}

@Composable
internal fun BoxWithConstraintsScope.AppliedStatus(
    status: List<ActiveStatusUIModel>,
    isPlayerStatus: Boolean,
    onClick: (ActiveStatusUIModel) -> Unit
) {
    if (status.isNotEmpty()) {
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(bottomStart = 8.dp, topStart = 8.dp)
                )
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            status.forEach { status ->
                val borderColor = if (isPlayerStatus) {
                    if (status is CharActiveStatusUIModel) PositiveStatus else NegativeStatus
                } else {
                    if (status is MobActiveStatusUIModel) PositiveStatus else NegativeStatus
                }

                BattleAsyncImage(
                    model = status.skillImage,
                    contentDescription = status.skillName,
                    modifier = Modifier
                        .size(20.dp)
                        .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
                        )
                        .clickable { onClick(status) }
                )
            }
        }
    }
}

@Composable
private fun BoxWithConstraintsScope.EnemyInfo(mob: BattleMobUIModel, containerWidth: Dp) {
    Column(
        modifier = Modifier
            .matchParentSize()
            .padding(INFO_PADDING),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.level_label, mob.level),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = getLevelStyle(containerWidth),
            color = HighlightOnImage
        )

        Text(
            text = mob.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = getNameStyle(containerWidth),
            color = OnSurfaceVariantOnImage
        )

        Spacer(modifier = Modifier.height(4.dp))

        HealthBar(
            actualHealth = mob.actualHealth,
            totalHealth = mob.totalHealth,
            progress = mob.healthProgress
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EnemyItemPreview() {
    EnemyItem(
        mob = HistoryModeBattlePreviewData.mockMobWarrior,
        modifier = Modifier.height(300.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun EnemySectionPreview() {
    EnemySection(
        state = HistoryModeBattlePreviewData.uiState,
        onMobClick = {},
        onStatusClick = {},
        onDismissDotTooltip = {},
        windowSizeClass = null,
        modifier = Modifier.height(400.dp)
    )
}
