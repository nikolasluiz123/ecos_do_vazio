package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.BattleAsyncImage
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.MobPhaseInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.PictureSlotGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SkillBattleStrokeColor

private val PROFILE_IMAGE_SIZE = 64.dp
private val IMAGE_CORNER_RADIUS = 4.dp
private val IMAGE_BORDER_WIDTH = 2.dp

@Composable
internal fun MobPhaseInfoHeader(
    mobPhaseInfo: MobPhaseInfoUIModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier
                .size(PROFILE_IMAGE_SIZE)
                .clip(RoundedCornerShape(IMAGE_CORNER_RADIUS))
                .background(PictureSlotGradient)
                .border(
                    width = IMAGE_BORDER_WIDTH,
                    color = SkillBattleStrokeColor,
                    shape = RoundedCornerShape(IMAGE_CORNER_RADIUS),
                ),
            contentAlignment = Alignment.Center,
        ) {
            BattleAsyncImage(
                model = mobPhaseInfo.mobProfileImage,
                contentDescription = mobPhaseInfo.mobName,
                modifier = Modifier.fillMaxSize(),
                filterQuality = FilterQuality.Low,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = mobPhaseInfo.mobCount,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Highlight,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                    ),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = mobPhaseInfo.mobName,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Highlight,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = mobPhaseInfo.mobDescription,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SecondaryTextColor,
                ),
            )
        }
    }
}
