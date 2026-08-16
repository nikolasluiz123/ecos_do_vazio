package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers.SkillsHorizontalPager
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers.SkillsVerticalPager
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs.SkillsHorizontalTabRow
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs.SkillsVerticalTabRow
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs.rememberSkillsPagerState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SurfaceVariantGradient

internal const val SKILLS_ANIMATION_DURATION = 600

internal val SKILL_ITEM_MIN_SIZE = 80.dp
internal val GRID_SPACING = 8.dp
internal val GRID_PADDING = 8.dp

internal val TAB_BAR_SIZE = 56.dp
internal val TAB_ICON_SIZE = 32.dp

@Composable
fun SkillsLazyVerticalGrid(
    state: HistoryModeBattleUIState,
    modifier: Modifier = Modifier,
    onSkillLongClick: (CharSkillUIModel) -> Unit = {},
    onDismissSkillTooltip: () -> Unit = {}
) {
    val pagerState = rememberSkillsPagerState()

    AnimatedVisibility(
        visible = !state.isLoading,
        enter = VerticalGridEnterTransition,
        exit = VerticalGridExitTransition,
        modifier = modifier
    ) {
        SkillsSurface {
            Column(modifier = Modifier.fillMaxSize()) {
                SkillsHorizontalTabRow(pagerState = pagerState)

                SkillsHorizontalPager(
                    state = state,
                    pagerState = pagerState,
                    modifier = Modifier.weight(1f),
                    onSkillLongClick = onSkillLongClick
                )
            }
        }
    }

    state.selectedSkill?.let { skill ->
        SkillTooltip(
            skill = skill,
            onDismissRequest = onDismissSkillTooltip
        )
    }
}

@Composable
fun SkillsLazyHorizontalGrid(
    state: HistoryModeBattleUIState,
    modifier: Modifier = Modifier,
    onSkillLongClick: (CharSkillUIModel) -> Unit = {},
    onDismissSkillTooltip: () -> Unit = {}
) {
    val pagerState = rememberSkillsPagerState()

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

                SkillsVerticalPager(
                    state = state,
                    pagerState = pagerState,
                    modifier = Modifier.weight(1f),
                    onSkillLongClick = onSkillLongClick
                )
            }
        }
    }

    state.selectedSkill?.let { skill ->
        SkillTooltip(
            skill = skill,
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