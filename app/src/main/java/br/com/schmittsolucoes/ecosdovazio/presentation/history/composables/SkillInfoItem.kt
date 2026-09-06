package br.com.schmittsolucoes.ecosdovazio.presentation.history.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AppAsyncImage
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.extensions.removeDamageFormula
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SkillBattleStrokeColor

private val SKILL_IMAGE_SIZE = 48.dp
private val IMAGE_CORNER_RADIUS = 4.dp
private val IMAGE_BORDER_WIDTH = 2.dp

@Composable
fun SkillInfoItem(
    @DrawableRes drawableRes: Int,
    name: String,
    description: String,
    blocked: Boolean,
    modifier: Modifier = Modifier,
    filterQuality: FilterQuality = FilterQuality.Medium,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(SKILL_IMAGE_SIZE)
                .clip(RoundedCornerShape(IMAGE_CORNER_RADIUS))
                .border(
                    width = IMAGE_BORDER_WIDTH,
                    color = SkillBattleStrokeColor,
                    shape = RoundedCornerShape(IMAGE_CORNER_RADIUS),
                ),
            contentAlignment = Alignment.Center,
        ) {
            AppAsyncImage(
                model = drawableRes,
                contentDescription = name,
                modifier = Modifier.fillMaxSize(),
                filterQuality = filterQuality,
                colorFilter = if (blocked) {
                    ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                } else null,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = description.removeDamageFormula(),
            style = MaterialTheme.typography.bodyMedium.copy(
                color = SecondaryTextColor,
            ),
            modifier = Modifier.weight(1f),
        )
    }
}

@Preview(name = "Light", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun SkillInfoItemPreview() {
    EcosDoVazioTheme {
        SkillInfoItem(
            drawableRes = R.drawable.skill_ataque_rapido,
            name = "Ataque Rápido",
            description = "Um ataque rápido que causa dano ao alvo.",
            blocked = false,
        )
    }
}
