package br.com.schmittsolucoes.ecosdovazio.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = Highlight,
            strokeWidth = 2.dp,
        )
    }
}
