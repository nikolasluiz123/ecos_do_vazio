package br.com.schmittsolucoes.ecosdovazio.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.min
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageScope

@Composable
fun AppAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    filterQuality: FilterQuality = FilterQuality.Medium,
    colorFilter: ColorFilter? = null,
    loading: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit)? = { DefaultLoading() },
) {
    if (LocalInspectionMode.current && model is Int) {
        Image(
            painter = painterResource(id = model),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            colorFilter = colorFilter,
        )
    } else {
        SubcomposeAsyncImage(
            model = model,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale,
            filterQuality = filterQuality,
            colorFilter = colorFilter,
            loading = loading,
        )
    }
}

@Composable
private fun DefaultLoading() {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val minDimension = min(maxWidth, maxHeight)

        val indicatorSize = if (minDimension.isSpecified && minDimension < Dp.Infinity) {
            (minDimension * 0.4f).coerceIn(16.dp, 48.dp)
        } else {
            40.dp
        }

        val strokeWidth = if (indicatorSize < 24.dp) 1.5.dp else 2.dp

        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize),
            color = Highlight,
            strokeWidth = strokeWidth,
        )
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun DefaultLoadingPreview() {
    EcosDoVazioTheme {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.DarkGray)
            ) {
                DefaultLoading()
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(64.dp)
                    .background(Color.DarkGray)
            ) {
                DefaultLoading()
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(120.dp)
                    .background(Color.DarkGray)
            ) {
                DefaultLoading()
            }
        }
    }
}
