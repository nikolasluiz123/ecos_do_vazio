package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun AppProgressBar(
    progress: () -> Float,
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        progress = progress,
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
    )
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun AppProgressBarPreview() {
    EcosDoVazioTheme {
        AppProgressBar(
            progress = { 0.6f }
        )
    }
}
