package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import br.com.schmittsolucoes.ecosdovazio.R

@Composable
internal fun rememberSkillsPagerState(): PagerState {
    return rememberPagerState(pageCount = { getTabIcons().size })
}

internal fun getTabIcons(): List<Int> {
    return listOf(
        R.drawable.ic_damage_24dp,
        R.drawable.ic_buff_24dp,
        R.drawable.ic_debuff_24dp
    )
}