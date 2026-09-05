package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AppAsyncImage

@Composable
internal fun BattleAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    filterQuality: FilterQuality = FilterQuality.High,
    colorFilter: ColorFilter? = null
) {
    AppAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        filterQuality = filterQuality,
        colorFilter = colorFilter,
    )
}
