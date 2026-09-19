package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.CHAR_AND_MOBS_BORDER_WIDTH
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.HistoryModeBattlePreviewData
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.INFO_PADDING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_ASPECT_RATIO
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_CORNER_RADIUS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_MAX_HEIGHT
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.PULSE_ALPHA_INITIAL
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.PULSE_ALPHA_TARGET
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.PULSE_ANIMATION_DURATION
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.SECTION_PADDING_VERTICAL
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getLevelStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getNameStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.CharacterBattleStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightOnImage
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OnSurfaceVariantOnImage
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.PictureSlotGradient

@Composable
internal fun CharSection(
    char: BattleCharUIModel?,
    onStatusClick: (ActiveStatusUIModel) -> Unit,
    modifier: Modifier = Modifier,
    isEnemyRound: Boolean = false,
    alignment: Alignment = Alignment.Center
) {
    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        if (char != null) {
            CharItem(
                char = char,
                isEnemyRound = isEnemyRound,
                onStatusClick = onStatusClick,
                modifier = Modifier
                    .heightIn(max = ITEM_MAX_HEIGHT)
                    .fillMaxHeight()
                    .padding(vertical = SECTION_PADDING_VERTICAL)
            )
        } else {
            CharItemLoading(
                modifier = Modifier
                    .heightIn(max = ITEM_MAX_HEIGHT)
                    .fillMaxHeight()
                    .padding(vertical = SECTION_PADDING_VERTICAL)
            )
        }
    }
}

@Composable
private fun CharItem(
    char: BattleCharUIModel,
    onStatusClick: (ActiveStatusUIModel) -> Unit,
    modifier: Modifier = Modifier,
    isEnemyRound: Boolean = false
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

    val borderColor = if (isEnemyRound) {
        OrangeForDetails.copy(alpha = alpha)
    } else {
        CharacterBattleStrokeColor
    }

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(ITEM_ASPECT_RATIO)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .background(PictureSlotGradient)
            .border(
                width = CHAR_AND_MOBS_BORDER_WIDTH,
                color = borderColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
            .padding(CHAR_AND_MOBS_BORDER_WIDTH)
    ) {
        BattleAsyncImage(
            model = char.battleImage,
            contentDescription = char.name,
            modifier = Modifier.fillMaxSize()
        )

        AppliedStatus(
            status = char.activeStatus,
            isPlayerStatus = true,
            onClick = onStatusClick
        )

        CharInfo(char, maxWidth)
    }
}

@Composable
private fun CharItemLoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(ITEM_ASPECT_RATIO)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .background(PictureSlotGradient)
            .border(
                width = CHAR_AND_MOBS_BORDER_WIDTH,
                color = CharacterBattleStrokeColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
            .padding(CHAR_AND_MOBS_BORDER_WIDTH),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Highlight,
            strokeWidth = 2.dp,
        )
    }
}

@Composable
private fun BoxScope.CharInfo(char: BattleCharUIModel, containerWidth: Dp) {
    Column(
        modifier = Modifier
            .matchParentSize()
            .padding(INFO_PADDING),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.level_label, char.level),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = getLevelStyle(containerWidth),
            color = HighlightOnImage
        )

        Text(
            text = char.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = getNameStyle(containerWidth),
            color = OnSurfaceVariantOnImage
        )

        Spacer(modifier = Modifier.height(4.dp))

        HealthBar(
            actualHealth = char.actualHealth,
            totalHealth = char.totalHealth,
            progress = char.healthProgress
        )
    }
}

@Preview(name = "Light Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CharItemPreview() {
    EcosDoVazioTheme {
        CharItem(
            char = HistoryModeBattlePreviewData.mockChar,
            onStatusClick = {},
            modifier = Modifier.height(300.dp)
        )
    }
}

@Preview(name = "Loading - Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Loading - Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CharItemLoadingPreview() {
    EcosDoVazioTheme {
        CharItemLoading(
            modifier = Modifier.height(300.dp)
        )
    }
}
