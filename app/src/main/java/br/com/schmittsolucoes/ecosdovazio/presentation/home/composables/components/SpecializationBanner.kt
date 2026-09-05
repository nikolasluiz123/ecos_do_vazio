package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun SpecializationBanner(
    onClickSelectSpecialization: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomeBannerCard(
        title = stringResource(id = R.string.specialization_banner_title),
        description = stringResource(id = R.string.specialization_banner_description),
        buttonText = stringResource(id = R.string.specialization_banner_button),
        onClickButton = onClickSelectSpecialization,
        modifier = modifier
    )
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
