package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_BORDER_WIDTH
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_CORNER_RADIUS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SkillBattleStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SurfaceVariantGradient
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun SkillsLazyVerticalGrid(
    state: HistoryModeBattleUIState,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { getTabIcons().size })

    AnimatedVisibility(
        visible = !state.isLoading,
        enter = VerticalGridEnterTransition,
        exit = VerticalGridExitTransition,
        modifier = modifier
    ) {
        SkillsSurface {
            Column(modifier = Modifier.fillMaxSize()) {
                SkillsHorizontalTabRow(pagerState = pagerState)

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top
                ) { page ->
                    val skills = getSkillsList(page, state)

                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val availableWidth = maxWidth - (GRID_PADDING * 2)
                        val availableHeight = maxHeight - (GRID_PADDING * 2)

                        val maxColumns = maxOf(
                            1,
                            ((availableWidth + GRID_SPACING) / (SKILL_ITEM_MIN_SIZE + GRID_SPACING)).toInt()
                        )

                        var columns = maxColumns

                        for (c in 1..maxColumns) {
                            val itemWidth = (availableWidth - (GRID_SPACING * (c - 1))) / c
                            val rowsCount = (skills.size + c - 1) / c
                            val totalHeight = (itemWidth * rowsCount) + (GRID_SPACING * (rowsCount - 1))

                            if (totalHeight <= availableHeight) {
                                columns = c
                                break
                            }
                        }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(columns),
                            contentPadding = PaddingValues(GRID_PADDING),
                            horizontalArrangement = Arrangement.spacedBy(GRID_SPACING),
                            verticalArrangement = Arrangement.spacedBy(GRID_SPACING),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(skills) { skill ->
                                SkillItem(skill = skill)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SkillsLazyHorizontalGrid(
    state: HistoryModeBattleUIState,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { getTabIcons().size })

    AnimatedVisibility(
        visible = !state.isLoading,
        enter = HorizontalGridEnterTransition,
        exit = HorizontalGridExitTransition,
        modifier = modifier
    ) {
        SkillsSurface {
            Row(modifier = Modifier.fillMaxSize()) {
                SkillsVerticalTabRow(
                    pagerState = pagerState,
                    modifier = Modifier.fillMaxHeight()
                )

                VerticalPager(
                    state = pagerState,
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.Start
                ) { page ->
                    val skills = getSkillsList(page, state)

                    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val availableWidth = maxWidth - (GRID_PADDING * 2)
                        val availableHeight = maxHeight - (GRID_PADDING * 2)

                        val maxRows = maxOf(
                            1,
                            ((availableHeight + GRID_SPACING) / (SKILL_ITEM_MIN_SIZE + GRID_SPACING)).toInt()
                        )

                        var rows = maxRows

                        for (r in 1..maxRows) {
                            val itemHeight = (availableHeight - (GRID_SPACING * (r - 1))) / r
                            val colsCount = (skills.size + r - 1) / r
                            val totalWidth = (itemHeight * colsCount) + (GRID_SPACING * (colsCount - 1))
                            if (totalWidth <= availableWidth) {
                                rows = r
                                break
                            }
                        }

                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(rows),
                            contentPadding = PaddingValues(GRID_PADDING),
                            horizontalArrangement = Arrangement.spacedBy(GRID_SPACING),
                            verticalArrangement = Arrangement.spacedBy(GRID_SPACING),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(skills) { skill ->
                                SkillItem(skill = skill)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SkillsHorizontalTabRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = getTabIcons()

    SecondaryScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier.height(TAB_BAR_SIZE),
        containerColor = Color.Transparent,
        divider = { },
    ) {
        tabs.forEachIndexed { index, drawable ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(
                            page = index,
                            animationSpec = TabRowAnimationSpec
                        )
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = drawable),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(TAB_ICON_SIZE)
                    )
                }
            )
        }
    }
}

@Composable
private fun SkillsVerticalTabRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val tabs = getTabIcons()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .width(TAB_BAR_SIZE)
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clipToBounds()
                .clickable {
                    coroutineScope.launch {
                        val next = (pagerState.currentPage + 1) % tabs.size
                        pagerState.animateScrollToPage(next)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            val fullHeight = maxHeight

            tabs.forEachIndexed { index, drawable ->
                Icon(
                    painter = painterResource(id = drawable),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(TAB_ICON_SIZE)
                        .graphicsLayer {
                            val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
                            val pageOffset = index - scrollPosition

                            translationY = pageOffset * fullHeight.toPx()
                            alpha = (1f - abs(pageOffset)).coerceIn(0f, 1f)
                        }
                )
            }
        }

        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.primary)
        )
    }
}

private fun getTabIcons(): List<Int> {
    return listOf(
        R.drawable.ic_damage_24dp,
        R.drawable.ic_buff_24dp,
        R.drawable.ic_debuff_24dp
    )
}

private fun getSkillsList(page: Int, state: HistoryModeBattleUIState): List<CharSkillUIModel> {
    return when (page) {
        0 -> state.damageSkills
        1 -> state.buffSkills
        else -> state.debuffSkills
    }
}

@Composable
private fun SkillsSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { }
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp,
        shadowElevation = 4.dp,
    ) {
        Box(modifier = Modifier.background(SurfaceVariantGradient)) {
            content()
        }
    }
}

@Composable
private fun SkillItem(
    skill: CharSkillUIModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .border(
                width = ITEM_BORDER_WIDTH,
                color = SkillBattleStrokeColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
    ) {
        BattleAsyncImage(
            model = skill.image,
            contentDescription = skill.name,
            modifier = Modifier.fillMaxSize(),
            filterQuality = FilterQuality.Medium
        )
    }
}

private const val SKILLS_ANIMATION_DURATION = 600
private val SKILL_ITEM_MIN_SIZE = 80.dp
private val GRID_SPACING = 8.dp
private val GRID_PADDING = 8.dp

private val TAB_BAR_SIZE = 56.dp
private val TAB_ICON_SIZE = 32.dp

private val TabRowAnimationSpec = spring(
    stiffness = Spring.StiffnessMediumLow,
    visibilityThreshold = 0.1f
)

private val VerticalGridEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            expandHorizontally(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                expandFrom = Alignment.End
            )

private val VerticalGridExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            shrinkHorizontally(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                shrinkTowards = Alignment.End
            )

private val HorizontalGridEnterTransition: EnterTransition =
    fadeIn(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            expandVertically(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                expandFrom = Alignment.Bottom
            )

private val HorizontalGridExitTransition: ExitTransition =
    fadeOut(animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing)) +
            shrinkVertically(
                animationSpec = tween(SKILLS_ANIMATION_DURATION, easing = FastOutSlowInEasing),
                shrinkTowards = Alignment.Bottom
            )
