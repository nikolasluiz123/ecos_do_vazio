package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills.pagers

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_BORDER_WIDTH
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_CORNER_RADIUS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.BattleAsyncImage
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SkillBattleStrokeColor

@Composable
internal fun SkillItem(
    skill: CharSkillUIModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .border(
                width = ITEM_BORDER_WIDTH,
                color = SkillBattleStrokeColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
    ) {
        BattleAsyncImage(
            model = skill.image,
            contentDescription = skill.name,
            modifier = Modifier.fillMaxSize(),
            filterQuality = FilterQuality.Medium
        )
    }
}