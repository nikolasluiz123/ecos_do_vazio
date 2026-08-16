package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.TAB_BAR_SIZE
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.TAB_ICON_SIZE
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun SkillsVerticalTabRow(
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val tabs = getTabIcons()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = modifier
            .width(TAB_BAR_SIZE)
            .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clipToBounds()
                .clickable {
                    coroutineScope.launch {
                        val next = (pagerState.currentPage + 1) % tabs.size
                        pagerState.animateScrollToPage(next)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            val fullHeight = maxHeight

            tabs.forEachIndexed { index, drawable ->
                Icon(
                    painter = painterResource(id = drawable),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(TAB_ICON_SIZE)
                        .graphicsLayer {
                            val scrollPosition = pagerState.currentPage + pagerState.currentPageOffsetFraction
                            val pageOffset = index - scrollPosition

                            translationY = pageOffset * fullHeight.toPx()
                            alpha = (1f - abs(pageOffset)).coerceIn(0f, 1f)
                        }
                )
            }
        }

        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(color = MaterialTheme.colorScheme.primary)
        )
    }
}
