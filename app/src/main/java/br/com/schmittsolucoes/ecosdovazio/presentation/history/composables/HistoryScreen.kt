package br.com.schmittsolucoes.ecosdovazio.presentation.history.composables

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryPhaseUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.PhaseCardBorderColor
import coil.compose.SubcomposeAsyncImage

private const val PHASE_IMAGE_SIZE = 200

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onPhaseClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onPhaseClick = onPhaseClick
    )
}

@Composable
fun HistoryScreen(
    state: HistoryUIState = HistoryUIState(),
    onDismissErrorDialog: () -> Unit = {},
    onPhaseClick: (String) -> Unit = {}
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.phases) { phase ->
                    HistoryPhaseItem(
                        phase = phase,
                        onPhaseClick = onPhaseClick
                    )
                }
            }

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog
                )
            }
        }
    }
}

@Composable
private fun HistoryPhaseItem(
    phase: HistoryPhaseUIModel,
    onPhaseClick: (String) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Pulse")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
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
            onPhaseClick = onPhaseClick
        )
        PhaseInfoCard(phase)
    }
}

@Composable
private fun PhaseImage(
    borderColor: Color,
    phase: HistoryPhaseUIModel,
    onPhaseClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .size(PHASE_IMAGE_SIZE.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(4.dp, borderColor, RoundedCornerShape(8.dp))
            .then(if (!phase.isLocked) Modifier.clickable { onPhaseClick(phase.id) } else Modifier),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = phase.imageResId,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
            filterQuality = FilterQuality.Medium,
            colorFilter = if (phase.isLocked) {
                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            } else null,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = OrangeForDetails,
                        strokeWidth = 2.dp
                    )
                }
            }
        )
    }
}

@Composable
private fun PhaseInfoCard(phase: HistoryPhaseUIModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, PhaseCardBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width((PHASE_IMAGE_SIZE + 32).dp)
            .padding(top = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
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
