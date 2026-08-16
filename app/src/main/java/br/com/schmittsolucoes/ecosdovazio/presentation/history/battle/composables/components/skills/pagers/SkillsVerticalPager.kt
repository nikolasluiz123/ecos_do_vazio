package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.GRID_PADDING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.GRID_SPACING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.SKILL_ITEM_MIN_SIZE
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

@Composable
fun SkillsVerticalPager(
    state: HistoryModeBattleUIState,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onSkillClick: (CharSkillUIModel) -> Unit = {},
    onSkillLongClick: (CharSkillUIModel) -> Unit = {}
) {
    VerticalPager(
        state = pagerState,
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) { page ->
        val skills = getSkillsList(page, state)

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val rows = calculateFixedAxisCells(
                availableFixedAxisLimit = maxHeight - (GRID_PADDING * 2),
                availableScrollAxisLimit = maxWidth - (GRID_PADDING * 2),
                itemCount = skills.size,
                itemMinSize = SKILL_ITEM_MIN_SIZE,
                spacing = GRID_SPACING
            )

            LazyHorizontalGrid(
                rows = GridCells.Fixed(rows),
                contentPadding = PaddingValues(GRID_PADDING),
                horizontalArrangement = Arrangement.spacedBy(GRID_SPACING),
                verticalArrangement = Arrangement.spacedBy(GRID_SPACING),
                modifier = Modifier.fillMaxSize()
            ) {
                items(skills) { skill ->
                    SkillItem(
                        skill = skill,
                        onSkillClick = onSkillClick,
                        onSkillLongClick = onSkillLongClick
                    )
                }
            }
        }
    }
}
