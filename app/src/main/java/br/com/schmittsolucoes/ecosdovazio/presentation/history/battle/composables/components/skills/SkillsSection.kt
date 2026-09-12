package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.systemGestureExclusion
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers.SkillsHorizontalPager
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers.SkillsVerticalPager
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs.SkillsHorizontalTabRow
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs.SkillsVerticalTabRow
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs.rememberSkillsPagerState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SurfaceVariantGradient

internal const val SKILLS_ANIMATION_DURATION = 600
internal const val DEFAULT_LOADING_SKILL_COUNT = 6

internal val SKILL_ITEM_MIN_SIZE = 80.dp
internal val GRID_SPACING = 8.dp
internal val GRID_PADDING = 8.dp

internal val TAB_BAR_SIZE = 56.dp
internal val TAB_ICON_SIZE = 32.dp

@Composable
fun SkillsLazyVerticalGrid(
    state: HistoryModeBattleUIState,
    modifier: Modifier = Modifier,
    onSkillClick: (CharSkillUIModel) -> Unit = {},
    onSkillLongClick: (CharSkillUIModel) -> Unit = {},
    onDismissSkillTooltip: () -> Unit = {},
) {
    val pagerState = rememberSkillsPagerState(state)
    val density = LocalDensity.current
    val exclusionHeightPx = with(density) { 200.dp.toPx() }

    SkillsSurface(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            SkillsHorizontalTabRow(pagerState = pagerState, state = state)

            SkillsHorizontalPager(
                state = state,
                pagerState = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .systemGestureExclusion { coordinates ->
                        val height = coordinates.size.height.toFloat()
                        val width = coordinates.size.width.toFloat()

                        val top = (height - exclusionHeightPx) / 3f
                        val bottom = top + exclusionHeightPx

                        Rect(
                            left = 0f,
                            top = top,
                            right = width,
                            bottom = bottom,
                        )
                    },
                onSkillClick = onSkillClick,
                onSkillLongClick = onSkillLongClick,
            )
        }
    }

    state.selectedSkill?.let { skill ->
        SkillTooltip(
            skill = skill,
            char = state.char,
            onDismissRequest = onDismissSkillTooltip
        )
    }
}

@Composable
fun SkillsLazyHorizontalGrid(
    state: HistoryModeBattleUIState,
    modifier: Modifier = Modifier,
    onSkillClick: (CharSkillUIModel) -> Unit = {},
    onSkillLongClick: (CharSkillUIModel) -> Unit = {},
    onDismissSkillTooltip: () -> Unit = {}
) {
    val pagerState = rememberSkillsPagerState(state)

    SkillsSurface(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize()) {
            SkillsVerticalTabRow(
                pagerState = pagerState,
                state = state,
                modifier = Modifier.fillMaxHeight()
            )

            SkillsVerticalPager(
                state = state,
                pagerState = pagerState,
                modifier = Modifier.weight(1f),
                onSkillClick = onSkillClick,
                onSkillLongClick = onSkillLongClick
            )
        }
    }

    state.selectedSkill?.let { skill ->
        SkillTooltip(
            skill = skill,
            char = state.char,
            onDismissRequest = onDismissSkillTooltip
        )
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