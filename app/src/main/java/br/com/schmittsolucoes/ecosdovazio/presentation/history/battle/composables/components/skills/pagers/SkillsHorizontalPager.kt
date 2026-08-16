package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.GRID_PADDING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.GRID_SPACING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.SKILL_ITEM_MIN_SIZE
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

@Composable
fun SkillsHorizontalPager(
    state: HistoryModeBattleUIState,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    onSkillLongClick: (CharSkillUIModel) -> Unit = {}
) {
    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) { page ->
        val skills = getSkillsList(page, state)

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val columns = calculateFixedAxisCells(
                availableFixedAxisLimit = maxWidth - (GRID_PADDING * 2),
                availableScrollAxisLimit = maxHeight - (GRID_PADDING * 2),
                itemCount = skills.size,
                itemMinSize = SKILL_ITEM_MIN_SIZE,
                spacing = GRID_SPACING
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(GRID_PADDING),
                horizontalArrangement = Arrangement.spacedBy(GRID_SPACING),
                verticalArrangement = Arrangement.spacedBy(GRID_SPACING),
                modifier = Modifier.fillMaxSize()
            ) {
                items(skills) { skill ->
                    SkillItem(
                        skill = skill,
                        onSkillLongClick = onSkillLongClick
                    )
                }
            }
        }
    }
}
