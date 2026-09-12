package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers

import androidx.compose.ui.unit.Dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState

internal fun calculateFixedAxisCells(
    availableFixedAxisLimit: Dp,
    availableScrollAxisLimit: Dp,
    itemCount: Int,
    itemMinSize: Dp,
    spacing: Dp
): Int {
    val maxCells = maxOf(
        1,
        ((availableFixedAxisLimit + spacing) / (itemMinSize + spacing)).toInt()
    )

    for (cells in 1..maxCells) {
        val itemSize = (availableFixedAxisLimit - (spacing * (cells - 1))) / cells
        val crossAxisCount = (itemCount + cells - 1) / cells
        val totalScrollAxisSize = (itemSize * crossAxisCount) + (spacing * (crossAxisCount - 1))

        if (totalScrollAxisSize <= availableScrollAxisLimit) {
            return cells
        }
    }

    return maxCells
}

internal fun getSkillsList(page: Int, state: HistoryModeBattleUIState): List<CharSkillUIModel> {
    return when (page) {
        0 -> state.char?.damageSkills ?: emptyList()
        1 -> state.char?.buffSkills ?: emptyList()
        2 -> state.char?.debuffSkills ?: emptyList()
        else -> state.char?.healSkills ?: emptyList()
    }
}