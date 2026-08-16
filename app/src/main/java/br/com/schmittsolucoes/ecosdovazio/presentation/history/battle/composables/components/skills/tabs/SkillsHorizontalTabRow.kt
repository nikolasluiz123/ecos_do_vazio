package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.TAB_BAR_SIZE
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.TAB_ICON_SIZE
import kotlinx.coroutines.launch

@Composable
fun SkillsHorizontalTabRow(
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
