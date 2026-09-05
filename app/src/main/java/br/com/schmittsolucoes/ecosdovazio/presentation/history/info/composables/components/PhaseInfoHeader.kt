package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails

@Composable
internal fun PhaseInfoHeader(
    phaseName: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = phaseName,
        style = MaterialTheme.typography.headlineMedium.copy(
            color = OrangeForDetails,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
    )
}
