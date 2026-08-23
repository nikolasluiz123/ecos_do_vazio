package br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_ASPECT_RATIO
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SkillBattleStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.pictureTextHighlightBackground
import coil.compose.SubcomposeAsyncImage

private val ITEM_CORNER_RADIUS = 4.dp
private val SKILLS_BORDER_WIDTH = 2.dp

@Composable
fun CharSkillItem(
    skill: CharSkillDetailsUIModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .border(
                width = SKILLS_BORDER_WIDTH,
                color = SkillBattleStrokeColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = skill.image,
            contentDescription = skill.name,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            filterQuality = FilterQuality.Medium,
            colorFilter = if (skill.blocked) {
                ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
            } else null,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Highlight,
                        strokeWidth = 2.dp
                    )
                }
            },
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .background(color = pictureTextHighlightBackground)
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
