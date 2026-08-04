package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.ButtonContainer
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightOutline
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor

@Composable
fun ClassCard(
    classModel: ClassSelectionUIModel,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit = {}
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        ),
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
                    Image(
                        painter = painterResource(id = classModel.presentationDrawableId),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(ShapeDefaults.Small),
                        contentScale = ContentScale.Crop
                    )

                    ClassSelectionDivider(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .widthIn(max = 200.dp)
                    )
                }

                Text(
                    text = classModel.name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Highlight
                    ),
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = classModel.description,
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

                ElevatedButton(
                    onClick = { onSelect(classModel.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(50.dp),
                    shape = RectangleShape,
                    border = BorderStroke(1.dp, HighlightOutline),
                    colors = ButtonDefaults.elevatedButtonColors(
                        contentColor = Highlight,
                        containerColor = ButtonContainer
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.select_button).uppercase(),
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun ClassCardPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        ClassCard(
            classModel = ClassSelectionUIModel(
                id = "1",
                name = "Guerreiro",
                description = "Especialista em combate corpo a corpo, atua na linha de frente equipado com armaduras pesadas. O nível de proteção e o estilo de jogo variam de acordo com o caminho escolhido.",
                presentationDrawableId = android.R.drawable.ic_menu_gallery
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ClassCardPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        ClassCard(
            classModel = ClassSelectionUIModel(
                id = "1",
                name = "Guerreiro",
                description = "Especialista em combate corpo a corpo, atua na linha de frente equipado com armaduras pesadas. O nível de proteção e o estilo de jogo variam de acordo com o caminho escolhido.",
                presentationDrawableId = android.R.drawable.ic_menu_gallery
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

