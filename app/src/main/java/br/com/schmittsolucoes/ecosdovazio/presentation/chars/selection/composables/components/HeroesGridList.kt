package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables.CharSelectionPreviewData
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.model.CharSelectionUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun HeroesGridList(
    chars: List<CharSelectionUIModel>,
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToClassSelection: () -> Unit,
    onCharSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val columns = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        else -> 3
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            YourHeroesHeader()
        }

        items(chars) { charModel ->
            HeroSlot(
                charModel = charModel,
                onClick = {
                    if (it.id == null) {
                        onNavigateToClassSelection()
                    } else {
                        onCharSelected(it.id)
                    }
                },
            )
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HeroesGridListPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        Box(modifier = Modifier.background(BackgroundGradient)) {
            HeroesGridList(
                chars = CharSelectionPreviewData.charList,
                windowWidthSizeClass = WindowWidthSizeClass.Compact,
                onNavigateToClassSelection = {},
                onCharSelected = {},
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun HeroesGridListPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        Box(modifier = Modifier.background(BackgroundGradient)) {
            HeroesGridList(
                chars = CharSelectionPreviewData.charList,
                windowWidthSizeClass = WindowWidthSizeClass.Compact,
                onNavigateToClassSelection = {},
                onCharSelected = {},
            )
        }
    }
}
