package br.com.schmittsolucoes.ecosdovazio.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.ButtonContainer
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightOutline
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightedButtonContent

@Composable
fun FilledHighlightedElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, HighlightOutline),
        colors = ButtonDefaults.elevatedButtonColors(
            contentColor = HighlightedButtonContent,
            containerColor = ButtonContainer
        )
    ) {
        Text(text = text.uppercase())
    }
}
