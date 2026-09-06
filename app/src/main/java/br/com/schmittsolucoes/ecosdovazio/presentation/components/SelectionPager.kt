package br.com.schmittsolucoes.ecosdovazio.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails

@Composable
fun SelectionPager(
    items: List<SelectionItemUIModel>,
    onSelectItem: (String) -> Unit,
    modifier: Modifier = Modifier,
    onCardClick: (SelectionItemUIModel) -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
            pageSpacing = 12.dp,
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            SelectionCard(
                item = items[page],
                onSelect = onSelectItem,
                onCardClick = onCardClick,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(items.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) OrangeForDetails else Color.Gray.copy(alpha = 0.3f)
                Box(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .size(if (pagerState.currentPage == iteration) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun SelectionPagerPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        SelectionPager(
            items = listOf(
                SelectionItemUIModel(
                    id = "1",
                    name = "Guerreiro",
                    description = "Especialista em combate corpo a corpo.",
                    presentationDrawableId = R.drawable.classe_guerreiro
                ),
                SelectionItemUIModel(
                    id = "2",
                    name = "Mago",
                    description = "Mestre em feitiços.",
                    presentationDrawableId = R.drawable.classe_mago
                )
            ),
            onSelectItem = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SelectionPagerPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        SelectionPager(
            items = listOf(
                SelectionItemUIModel(
                    id = "1",
                    name = "Guerreiro",
                    description = "Especialista em combate corpo a corpo.",
                    presentationDrawableId = R.drawable.classe_guerreiro
                ),
                SelectionItemUIModel(
                    id = "2",
                    name = "Mago",
                    description = "Mestre em feitiços.",
                    presentationDrawableId = R.drawable.classe_mago
                )
            ),
            onSelectItem = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
