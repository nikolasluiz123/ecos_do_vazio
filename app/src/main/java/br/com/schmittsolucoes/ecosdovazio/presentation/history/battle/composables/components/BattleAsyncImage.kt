package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_ASPECT_RATIO
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import coil.compose.SubcomposeAsyncImage

@Composable
internal fun BattleAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    filterQuality: FilterQuality = FilterQuality.High
) {
    if (LocalInspectionMode.current && model is Int) {
        Image(
            painter = painterResource(id = model),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        SubcomposeAsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop,
            filterQuality = filterQuality,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(ITEM_ASPECT_RATIO, matchHeightConstraintsFirst = true),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Highlight,
                        strokeWidth = 2.dp
                    )
                }
            },
        )
    }
}
