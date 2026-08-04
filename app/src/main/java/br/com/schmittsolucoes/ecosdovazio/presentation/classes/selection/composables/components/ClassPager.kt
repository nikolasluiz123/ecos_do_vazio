package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components

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
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails

@Composable
fun ClassPager(
    classes: List<ClassSelectionUIModel>,
    onSelectClass: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { classes.size })

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 24.dp),
            pageSpacing = 24.dp,
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) { page ->
            ClassCard(
                classModel = classes[page],
                onSelect = onSelectClass,
                modifier = Modifier.fillMaxSize()
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(classes.size) { iteration ->
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
fun ClassPagerPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        ClassPager(
            classes = listOf(
                ClassSelectionUIModel(
                    id = "1",
                    name = "Guerreiro",
                    description = "Especialista em combate corpo a corpo.",
                    presentationDrawableId = android.R.drawable.ic_menu_gallery
                ),
                ClassSelectionUIModel(
                    id = "2",
                    name = "Mago",
                    description = "Mestre em feitiços.",
                    presentationDrawableId = android.R.drawable.ic_menu_gallery
                )
            ),
            onSelectClass = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ClassPagerPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        ClassPager(
            classes = listOf(
                ClassSelectionUIModel(
                    id = "1",
                    name = "Guerreiro",
                    description = "Especialista em combate corpo a corpo.",
                    presentationDrawableId = android.R.drawable.ic_menu_gallery
                ),
                ClassSelectionUIModel(
                    id = "2",
                    name = "Mago",
                    description = "Mestre em feitiços.",
                    presentationDrawableId = android.R.drawable.ic_menu_gallery
                )
            ),
            onSelectClass = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
