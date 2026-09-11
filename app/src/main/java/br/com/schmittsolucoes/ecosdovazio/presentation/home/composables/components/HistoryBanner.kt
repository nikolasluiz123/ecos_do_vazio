package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components.AppProgressBar
import br.com.schmittsolucoes.ecosdovazio.presentation.components.FilledHighlightedElevatedButton
import br.com.schmittsolucoes.ecosdovazio.presentation.history.model.LastUnfinishedHistoryPhaseUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.animations.AnimatedVerticalSlideContent

@Composable
fun HistoryBanner(
    model: LastUnfinishedHistoryPhaseUIModel,
    onNavigateToBattle: (String) -> Unit,
    onNavigateToMobsInfo: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        HomeBannerCard(
            title = stringResource(id = R.string.history_banner_title),
            modifier = Modifier.fillMaxHeight()
        ) {
            if (model.isCompleted) {
                CompletedLayout(model)
            } else {
                ProgressLayout(model, onNavigateToBattle)
            }
        }

        if (!model.isCompleted && (model.phaseId != null)) {
            PhaseInfoIcon(
                onClick = { onNavigateToMobsInfo(model.phaseId) }
            )
        }
    }
}

@Composable
private fun CompletedLayout(model: LastUnfinishedHistoryPhaseUIModel) {
    var animatedCount by remember { mutableIntStateOf(0) }
    var animatedProgress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(model) {
        animatedCount = model.completedPhasesCount
        animatedProgress = 1f
    }

    Text(
        text = stringResource(id = R.string.history_banner_completed_description),
        style = MaterialTheme.typography.bodyMedium,
        color = SecondaryTextColor,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        val progressText = stringResource(
            id = R.string.history_banner_progress,
            animatedCount,
            model.totalPhasesCount
        )
        AnimatedVerticalSlideContent(
            targetState = progressText,
            label = "HistoryBannerProgressAnimation"
        ) { targetValue ->
            Text(
                text = targetValue,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Serif,
                    color = SecondaryTextColor
                )
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    AppProgressBar(progress = animatedProgress)
}

@Composable
private fun ColumnScope.ProgressLayout(
    model: LastUnfinishedHistoryPhaseUIModel,
    onNavigateToBattle: (String) -> Unit
) {
    var animatedCount by remember { mutableIntStateOf(0) }
    var animatedProgress by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(model) {
        animatedCount = model.completedPhasesCount
        animatedProgress = model.progress
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = model.phaseName,
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        val progressText = stringResource(
            id = R.string.history_banner_progress,
            animatedCount,
            model.totalPhasesCount
        )
        AnimatedVerticalSlideContent(
            targetState = progressText,
            label = "HistoryBannerProgressAnimation"
        ) { targetValue ->
            Text(
                text = targetValue,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = FontFamily.Serif,
                    color = SecondaryTextColor
                )
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    AppProgressBar(progress = animatedProgress)

    Spacer(modifier = Modifier.weight(1f))
    Spacer(modifier = Modifier.height(16.dp))

    model.phaseId?.let { phaseId ->
        FilledHighlightedElevatedButton(
            text = stringResource(id = R.string.history_banner_play_button),
            onClick = { onNavigateToBattle(phaseId) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun BoxScope.PhaseInfoIcon(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier.align(Alignment.TopEnd),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = stringResource(R.string.history_mobs_info_content_description),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(name = "Light Mode - In Progress", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
fun HistoryBannerPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        HistoryBanner(
            model = LastUnfinishedHistoryPhaseUIModel(
                phaseId = "1",
                phaseName = "Espada Lascada",
                completedPhasesCount = 2,
                totalPhasesCount = 10,
                progress = 0.2f,
                isCompleted = false
            ),
            onNavigateToBattle = {},
            onNavigateToMobsInfo = {}
        )
    }
}

@Preview(name = "Dark Mode - Completed", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HistoryBannerPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        HistoryBanner(
            model = LastUnfinishedHistoryPhaseUIModel(
                phaseId = null,
                phaseName = "",
                completedPhasesCount = 10,
                totalPhasesCount = 10,
                progress = 1f,
                isCompleted = true
            ),
            onNavigateToBattle = {},
            onNavigateToMobsInfo = {}
        )
    }
}
