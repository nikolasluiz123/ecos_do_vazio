package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
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

@Composable
fun SkillsLazyVerticalGrid(
    state: HistoryModeBattleUIState,
    windowSizeClass: WindowSizeClass?,
    modifier: Modifier = Modifier,
) {
    val columns = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> 1
        WindowWidthSizeClass.Medium -> 2
        else -> 3
    }

    val pagerState = rememberPagerState(pageCount = { 2 })

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

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(columns),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
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

@Composable
fun SkillsLazyHorizontalGrid(
    state: HistoryModeBattleUIState,
    windowSizeClass: WindowSizeClass?,
    modifier: Modifier = Modifier,
) {
    val rows = when (windowSizeClass?.heightSizeClass) {
        WindowHeightSizeClass.Compact -> 1
        WindowHeightSizeClass.Medium -> 2
        else -> 3
    }

    val pagerState = rememberPagerState(pageCount = { 2 })

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

                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(rows),
                        contentPadding = PaddingValues(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
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

@Composable
private fun SkillsHorizontalTabRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        R.drawable.ic_char_16dp,
        R.drawable.ic_home_16dp
    )

    SecondaryTabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onSurface,
        divider = { }
    ) {
        tabs.forEachIndexed { index, drawable ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Icon(
                        painter = painterResource(id = drawable),
                        contentDescription = null
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
    val coroutineScope = rememberCoroutineScope()
    val tabs = listOf(
        R.drawable.ic_char_16dp,
        R.drawable.ic_home_16dp
    )

    Column(
        modifier = modifier.width(64.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        tabs.forEachIndexed { index, drawable ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = drawable),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

private fun getSkillsList(page: Int, state: HistoryModeBattleUIState): List<CharSkillUIModel> {
    return if (page == 0) state.damageSkills else state.buffAndDebuffSkills
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
