package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.components.FilledHighlightedElevatedButton
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor

@Composable
fun SpecializationBanner(
    onClickSelectSpecialization: () -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeBannerCard(
        title = stringResource(id = R.string.specialization_banner_title),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.specialization_banner_description),
            style = MaterialTheme.typography.bodyMedium,
            color = SecondaryTextColor,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(16.dp))

        FilledHighlightedElevatedButton(
            text = stringResource(id = R.string.specialization_banner_button),
            onClick = onClickSelectSpecialization,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun SpecializationBannerPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        SpecializationBanner(
            onClickSelectSpecialization = {}
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SpecializationBannerPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        SpecializationBanner(
            onClickSelectSpecialization = {}
        )
    }
}
