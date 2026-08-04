package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.DividerColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun ClassSelectionDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = DividerColor
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_battle_20dp),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(16.dp),
            tint = Color.Unspecified
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = DividerColor
        )
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun ClassSelectionDividerPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        ClassSelectionDivider(
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ClassSelectionDividerPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        ClassSelectionDivider(
            modifier = Modifier.padding(16.dp)
        )
    }
}
