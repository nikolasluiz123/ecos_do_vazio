package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.SECTION_PADDING_VERTICAL
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getLevelStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getNameStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.CharacterBattleStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightOnImage
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OnSurfaceVariantOnImage

@Composable
internal fun CharSection(
    char: BattleCharUIModel?,
    onStatusClick: (ActiveStatusUIModel) -> Unit,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center
) {
    if (char == null) return

    Box(
        modifier = modifier,
        contentAlignment = alignment
    ) {
        CharItem(
            char = char,
            onStatusClick = onStatusClick,
            modifier = Modifier
                .heightIn(max = ITEM_MAX_HEIGHT)
                .fillMaxHeight()
                .padding(vertical = SECTION_PADDING_VERTICAL)
        )
    }
}

@Composable
private fun CharItem(
    char: BattleCharUIModel,
    onStatusClick: (ActiveStatusUIModel) -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(ITEM_ASPECT_RATIO)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .border(
                width = CHAR_AND_MOBS_BORDER_WIDTH,
                color = CharacterBattleStrokeColor,
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
            onClick = onStatusClick
        )

        CharInfo(char, maxWidth)
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

@Preview(showBackground = true)
@Composable
private fun CharItemPreview() {
    CharItem(
        char = HistoryModeBattlePreviewData.mockChar,
        onStatusClick = {},
        modifier = Modifier.height(300.dp)
    )
}
