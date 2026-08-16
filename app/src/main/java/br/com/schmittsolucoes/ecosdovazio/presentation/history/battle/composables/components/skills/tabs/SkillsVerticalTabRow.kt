package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.TAB_BAR_SIZE
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.TAB_ICON_SIZE
import kotlinx.coroutines.launch

@Composable
fun SkillsVerticalTabRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val tabs = getTabIcons()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .width(TAB_BAR_SIZE)
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            tabs.forEachIndexed { index, drawable ->
                Box(
                    modifier = Modifier
                        .size(TAB_BAR_SIZE)
                        .selectable(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = drawable),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(TAB_ICON_SIZE)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .width(3.dp)
                .height(TAB_BAR_SIZE)
                .graphicsLayer {
                    translationY = (pagerState.currentPage + pagerState.currentPageOffsetFraction) * TAB_BAR_SIZE.toPx()
                }
                .align(Alignment.TopEnd)
                .background(color = MaterialTheme.colorScheme.primary)
        )
    }
}
