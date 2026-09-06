package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AppBottomSheet
import br.com.schmittsolucoes.ecosdovazio.presentation.components.FilledHighlightedElevatedButton
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightedTextFieldBackground
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightedTextFieldBorder
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightedTextFieldText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharNamingBottomSheet(
    onDismissRequest: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    AppBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Text(
            text = stringResource(R.string.char_selection_how_will_it_be_recognized),
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Highlight,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        HeroNameTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        FilledHighlightedElevatedButton(
            text = stringResource(R.string.confirm_button),
            onClick = { onConfirm(name) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun HeroNameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val borderColor = HighlightedTextFieldBorder
    val backgroundColor = HighlightedTextFieldBackground

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = MaterialTheme.typography.headlineLarge.copy(
            color = HighlightedTextFieldText,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        ),
        cursorBrush = SolidColor(Highlight),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = backgroundColor, shape = RoundedCornerShape(8.dp))
                    .border(1.dp, borderColor, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = stringResource(R.string.char_selection_name_placeholder),
                        color = HighlightedTextFieldText.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                innerTextField()
            }
        }
    )
}
