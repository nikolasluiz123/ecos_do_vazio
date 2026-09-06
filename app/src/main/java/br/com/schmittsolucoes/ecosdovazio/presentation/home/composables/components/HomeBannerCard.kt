package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.components.FilledHighlightedElevatedButton
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor

@Composable
fun HomeBannerCard(
    title: String,
    modifier: Modifier = Modifier,
    titleColor: Color = Highlight,
    borderStroke: BorderStroke? = BorderStroke(1.dp, Highlight.copy(alpha = 0.5f)),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .widthIn(max = 480.dp),
        shape = ShapeDefaults.Medium,
        border = borderStroke,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = titleColor
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            content()
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun HomeBannerCardPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        HomeBannerCard(
            title = "Especialização Disponível!"
        ) {
            Text(
                text = "Seu herói atingiu o nível necessário. Escolha uma especialização para evoluir seus poderes!",
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryTextColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilledHighlightedElevatedButton(
                text = "Escolher Especialização",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeBannerCardPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        HomeBannerCard(
            title = "Especialização Disponível!"
        ) {
            Text(
                text = "Seu herói atingiu o nível necessário. Escolha uma especialização para evoluir seus poderes!",
                style = MaterialTheme.typography.bodyMedium,
                color = SecondaryTextColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            FilledHighlightedElevatedButton(
                text = "Escolher Especialização",
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
