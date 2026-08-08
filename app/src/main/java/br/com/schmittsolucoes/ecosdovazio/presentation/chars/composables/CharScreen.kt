package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharLevelInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroButtonStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight

@Composable
fun CharScreen(
    viewModel: CharViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharScreen(
        state = state,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun CharScreen(
    state: CharUIState = CharUIState(),
    onDismissErrorDialog: () -> Unit = {}
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
                .padding(16.dp),
        ) {

            state.levelInfo?.let { levelInfo ->
                CharLevelInfo(levelInfo = levelInfo)
            }

            Spacer(modifier = Modifier.height(24.dp))

            state.statusInfo?.let { statusInfo ->
                CharStatus(statusInfo = statusInfo)
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
fun CharLevelInfo(
    levelInfo: CharLevelInfoUIModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.char_level_label, levelInfo.level),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Serif
                )
            )
            Text(
                text = stringResource(
                    R.string.char_xp_label,
                    levelInfo.currentExperience,
                    levelInfo.nextLevelExperience
                ),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Serif
                )
            )
        }
        LinearProgressIndicator(
            progress = { levelInfo.progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            drawStopIndicator = { }
        )
    }
}

@Composable
fun CharStatus(
    statusInfo: CharStatusUIModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.char_status_title),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, HeroButtonStrokeColor, RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusItem(
                    label = stringResource(R.string.char_status_hp),
                    value = statusInfo.hp,
                    modifier = Modifier.weight(1f)
                )
                StatusItem(
                    label = stringResource(R.string.char_status_damage),
                    value = statusInfo.baseDamage,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusItem(
                    label = stringResource(R.string.char_status_phys_res),
                    value = statusInfo.physicalResistance,
                    modifier = Modifier.weight(1f)
                )
                StatusItem(
                    label = stringResource(R.string.char_status_mag_res),
                    value = statusInfo.magicResistance,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusItem(
                    label = stringResource(R.string.char_status_crit),
                    value = statusInfo.criticalChance,
                    modifier = Modifier.weight(1f)
                )
                StatusItem(
                    label = stringResource(R.string.char_status_dodge),
                    value = statusInfo.dodgeChance,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun StatusItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Highlight,
            )
        )
    }
}
