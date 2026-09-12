package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState

@Composable
internal fun rememberSkillsPagerState(state: HistoryModeBattleUIState): PagerState {
    val tabCount = remember(state.char) { getTabIcons(state).size }
    return rememberPagerState(pageCount = { tabCount })
}

internal fun getTabIcons(state: HistoryModeBattleUIState): List<Int> {
    val char = state.char ?: return emptyList()

    return listOfNotNull(
        R.drawable.ic_damage_24dp.takeIf { char.damageSkills.isNotEmpty() },
        R.drawable.ic_buff_24dp.takeIf { char.buffSkills.isNotEmpty() },
        R.drawable.ic_debuff_24dp.takeIf { char.debuffSkills.isNotEmpty() },
        R.drawable.ic_heal_24dp.takeIf { char.healSkills.isNotEmpty() },
    )
}