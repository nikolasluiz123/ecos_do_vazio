package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.HistoryModeBattlePreviewData
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.INFO_PADDING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_ASPECT_RATIO
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_BORDER_WIDTH
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_CORNER_RADIUS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_MAX_HEIGHT
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.ITEM_SPACING
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.SECTION_PADDING_VERTICAL
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getLevelStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.getNameStyle
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.CharacterBattleStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightOnImage
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OnSurfaceVariantOnImage

@Composable
internal fun EnemySection(
    mobs: List<BattleMobUIModel>,
    windowSizeClass: WindowSizeClass?,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(ITEM_SPACING)
) {
    if (mobs.isEmpty()) return

    val isExpanded = windowSizeClass?.widthSizeClass == WindowWidthSizeClass.Expanded

    if (isExpanded) {
        EnemyHorizontalList(modifier, horizontalArrangement, mobs)
    } else {
        EnemyHorizontalPager(mobs, modifier)
    }
}

@Composable
private fun EnemyHorizontalList(
    modifier: Modifier,
    horizontalArrangement: Arrangement.Horizontal,
    mobs: List<BattleMobUIModel>
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = ITEM_SPACING),
        horizontalArrangement = horizontalArrangement
    ) {
        items(mobs) { mob ->
            EnemyItem(
                mob = mob,
                modifier = Modifier
                    .heightIn(max = ITEM_MAX_HEIGHT)
                    .fillMaxHeight()
                    .padding(vertical = SECTION_PADDING_VERTICAL)
            )
        }
    }
}

@Composable
private fun EnemyHorizontalPager(
    mobs: List<BattleMobUIModel>,
    modifier: Modifier
) {
    val pagerState = rememberPagerState { mobs.size }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth(),
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                EnemyItem(
                    mob = mobs[page],
                    modifier = Modifier
                        .heightIn(max = ITEM_MAX_HEIGHT)
                        .fillMaxHeight()
                        .padding(vertical = SECTION_PADDING_VERTICAL)
                )
            }
        }

        PulsingArrow(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            isVisible = pagerState.canScrollBackward,
            modifier = Modifier
                .align(Alignment.CenterStart)
        )

        PulsingArrow(
            icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            isVisible = pagerState.canScrollForward,
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
private fun EnemyItem(
    mob: BattleMobUIModel,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(ITEM_ASPECT_RATIO)
            .clip(RoundedCornerShape(ITEM_CORNER_RADIUS))
            .border(
                width = ITEM_BORDER_WIDTH,
                color = CharacterBattleStrokeColor,
                shape = RoundedCornerShape(ITEM_CORNER_RADIUS)
            )
    ) {
        BattleAsyncImage(
            model = mob.image,
            contentDescription = mob.name,
            modifier = Modifier.fillMaxSize()
        )

        EnemyInfo(mob, maxWidth)
    }
}

@Composable
private fun BoxScope.EnemyInfo(mob: BattleMobUIModel, containerWidth: Dp) {
    Column(
        modifier = Modifier
            .matchParentSize()
            .padding(INFO_PADDING),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.level_label, mob.level),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = getLevelStyle(containerWidth),
            color = HighlightOnImage
        )

        Text(
            text = mob.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = getNameStyle(containerWidth),
            color = OnSurfaceVariantOnImage
        )

        Spacer(modifier = Modifier.height(4.dp))

        HealthBar(
            actualHealth = mob.actualHealth,
            totalHealth = mob.totalHealth,
            progress = mob.healthProgress
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EnemyItemPreview() {
    EnemyItem(
        mob = HistoryModeBattlePreviewData.mockMobWarrior,
        modifier = Modifier.height(300.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun EnemySectionPreview() {
    EnemySection(
        mobs = HistoryModeBattlePreviewData.mockMobsList,
        windowSizeClass = null,
        modifier = Modifier.height(400.dp)
    )
}
