package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_CORNER_RADIUS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.SKILLS_BORDER_WIDTH
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.BattleAsyncImage
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SkillBattleStrokeColor

@Composable
internal fun SkillItem(
    skill: CharSkillUIModel,
    modifier: Modifier = Modifier,
    onSkillClick: (CharSkillUIModel) -> Unit = {},
    onSkillLongClick: (CharSkillUIModel) -> Unit = {}
) {
    val applyBorderColor = skill.blocked || skill.currentRefreshTime > 0
    val onTapPermitted = !skill.blocked && skill.currentRefreshTime == 0
    val showOverlay = skill.currentRefreshTime > 0

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .border(
                width = SKILLS_BORDER_WIDTH,
                color = if (applyBorderColor) Color.Unspecified else SkillBattleStrokeColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
            .pointerInput(skill) {
                detectTapGestures(
                    onTap = if (onTapPermitted) { { onSkillClick(skill) } } else null,
                    onLongPress = { onSkillLongClick(skill) }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        SkillImage(skill)

        if (showOverlay) {
            CooldownOverlay(skill)
        }
    }
}

@Composable
private fun SkillImage(skill: CharSkillUIModel) {
    BattleAsyncImage(
        model = skill.image,
        contentDescription = skill.name,
        modifier = Modifier.fillMaxSize(),
        filterQuality = FilterQuality.Medium,
        colorFilter = if (skill.blocked) {
            ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
        } else null
    )
}

@Composable
private fun CooldownOverlay(skill: CharSkillUIModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = skill.currentRefreshTime.toString(),
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}