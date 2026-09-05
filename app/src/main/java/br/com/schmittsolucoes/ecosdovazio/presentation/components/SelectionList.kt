package br.com.schmittsolucoes.ecosdovazio.presentation.components

import android.R
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun SelectionList(
    items: List<SelectionItemUIModel>,
    onSelectItem: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items) { item ->
            SelectionCard(
                item = item,
                onSelect = onSelectItem,
                modifier = Modifier
                    .width(420.dp)
                    .fillMaxHeight()
            )
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true, widthDp = 1000)
@Composable
fun SelectionListPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        SelectionList(
            items = listOf(
                SelectionItemUIModel(
                    id = "1",
                    name = "Guerreiro",
                    description = "Especialista em combate corpo a corpo.",
                    presentationDrawableId = R.drawable.ic_menu_gallery
                ),
                SelectionItemUIModel(
                    id = "2",
                    name = "Mago",
                    description = "Mestre em feitiços.",
                    presentationDrawableId = R.drawable.ic_menu_gallery
                )
            ),
            onSelectItem = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true, widthDp = 1000)
@Composable
fun SelectionListPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        SelectionList(
            items = listOf(
                SelectionItemUIModel(
                    id = "1",
                    name = "Guerreiro",
                    description = "Especialista em combate corpo a corpo.",
                    presentationDrawableId = R.drawable.ic_menu_gallery
                ),
                SelectionItemUIModel(
                    id = "2",
                    name = "Mago",
                    description = "Mestre em feitiços.",
                    presentationDrawableId = R.drawable.ic_menu_gallery
                )
            ),
            onSelectItem = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
