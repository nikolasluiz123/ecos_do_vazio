package br.com.schmittsolucoes.ecosdovazio.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components.ClassSelectionDivider
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor

@Composable
fun SelectionCard(
    item: SelectionItemUIModel,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit = {},
    onCardClick: (SelectionItemUIModel) -> Unit = {}
) {
    Card(
        onClick = { onCardClick(item) },
        modifier = modifier,
        shape = ShapeDefaults.Medium,
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f))
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
        ) {
            val showVisualElements = maxHeight > 320.dp

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (showVisualElements) {
                    AppAsyncImage(
                        model = item.presentationDrawableId,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(ShapeDefaults.Small),
                        contentScale = ContentScale.Crop,
                        filterQuality = FilterQuality.Medium,
                    )

                    ClassSelectionDivider(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .widthIn(max = 200.dp)
                    )
                }

                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Highlight
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = SecondaryTextColor,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                if (!showVisualElements) {
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                FilledHighlightedElevatedButton(
                    text = stringResource(id = R.string.select_button),
                    onClick = { onSelect(item.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun SelectionCardPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        SelectionCard(
            item = SelectionItemUIModel(
                id = "1",
                name = "Guerreiro",
                description = "Especialista em combate corpo a corpo.",
                presentationDrawableId = android.R.drawable.ic_menu_gallery
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SelectionCardPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        SelectionCard(
            item = SelectionItemUIModel(
                id = "1",
                name = "Guerreiro",
                description = "Especialista em combate corpo a corpo.",
                presentationDrawableId = android.R.drawable.ic_menu_gallery
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
