package br.com.schmittsolucoes.ecosdovazio.presentation.history.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AppAsyncImage
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryPhaseUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.PhaseCardBorderColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.pictureTextHighlightBackground

private val PhaseImageSize = 200.dp
private val PhaseImageCornerRadius = 8.dp
private val PhaseImageBorderWidth = 4.dp

private val PhaseCardCornerRadius = 4.dp
private val PhaseCardBorderWidth = 1.dp
private val PhaseCardElevation = 2.dp
private val PhaseCardWidthAdjustment = 32.dp
private val PhaseCardTopPadding = 8.dp
private val PhaseCardContentVerticalPadding = 8.dp
private val PhaseCardContentHorizontalPadding = 4.dp

private const val PULSE_ANIMATION_DURATION = 1000
private const val PULSE_ALPHA_INITIAL = 0.4f
private const val PULSE_ALPHA_TARGET = 1f

@Composable
internal fun HistoryPhaseItem(
    phase: HistoryPhaseUIModel,
    onPhaseClick: (String) -> Unit,
    onInfoClick: (String) -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = PULSE_ALPHA_INITIAL,
        targetValue = PULSE_ALPHA_TARGET,
        animationSpec = infiniteRepeatable(
            animation = tween(PULSE_ANIMATION_DURATION, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Alpha"
    )

    val borderColor = if (phase.isActual || phase.isFinished) {
        OrangeForDetails.copy(alpha = if (phase.isActual) alpha else 1f)
    } else {
        Color.Transparent
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        PhaseImage(
            borderColor = borderColor,
            phase = phase,
            onPhaseClick = onPhaseClick,
            onInfoClick = onInfoClick
        )
        PhaseInfoCard(phase)
    }
}

@Composable
private fun PhaseImage(
    borderColor: Color,
    phase: HistoryPhaseUIModel,
    onPhaseClick: (String) -> Unit,
    onInfoClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(PhaseImageSize)
            .clip(RoundedCornerShape(PhaseImageCornerRadius))
            .border(PhaseImageBorderWidth, borderColor, RoundedCornerShape(PhaseImageCornerRadius))
            .then(if (!phase.isLocked) Modifier.clickable { onPhaseClick(phase.id) } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        AppAsyncImage(
            model = phase.imageResId,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            filterQuality = FilterQuality.Medium,
            colorFilter = if (phase.isLocked) {
                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            } else null,
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(28.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(pictureTextHighlightBackground)
                .clickable { onInfoClick(phase.id) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(R.string.history_mobs_info_content_description),
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun PhaseInfoCard(phase: HistoryPhaseUIModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(PhaseCardCornerRadius),
        border = BorderStroke(PhaseCardBorderWidth, PhaseCardBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = PhaseCardElevation),
        modifier = Modifier
            .width(PhaseImageSize + PhaseCardWidthAdjustment)
            .padding(top = PhaseCardTopPadding)
    ) {
        Column(
            modifier = Modifier.padding(
                vertical = PhaseCardContentVerticalPadding,
                horizontal = PhaseCardContentHorizontalPadding
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = phase.name,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = Highlight,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )

            val statusLabel = getStatusLabel(phase)

            statusLabel?.let { label ->
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily.Serif,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}

@Composable
private fun getStatusLabel(phase: HistoryPhaseUIModel): String? {
    return when {
        phase.isFinished -> stringResource(R.string.history_phase_status_finished)
        phase.isActual -> stringResource(R.string.history_phase_status_current)
        else -> null
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HistoryPhaseItemPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        HistoryPhaseItem(
            phase = HistoryPhaseUIModel(
                id = "1",
                name = "Espada Lascada",
                imageResId = R.drawable.icone_fase_espada_lascada,
                isFinished = false,
                isActual = true
            ),
            onPhaseClick = {},
            onInfoClick = {}
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun HistoryPhaseItemPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        HistoryPhaseItem(
            phase = HistoryPhaseUIModel(
                id = "1",
                name = "Espada Lascada",
                imageResId = R.drawable.icone_fase_espada_lascada,
                isFinished = false,
                isActual = true
            ),
            onPhaseClick = {},
            onInfoClick = {}
        )
    }
}
