package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.CharacterBattleStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HealthBarRedEnd
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HealthBarRedStart
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HealthBarTrack
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HighlightOnImage
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OnSurfaceVariantOnImage
import coil.compose.SubcomposeAsyncImage

@Composable
fun HistoryModeBattleScreen(
    viewModel: HistoryModeBattleViewModel,
    windowSizeClass: WindowSizeClass
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryModeBattleScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun HistoryModeBattleScreen(
    state: HistoryModeBattleUIState = HistoryModeBattleUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {}
) {
    val isExpandedWidth = windowSizeClass?.widthSizeClass == WindowWidthSizeClass.Expanded
    val isCompactHeight = windowSizeClass?.heightSizeClass == WindowHeightSizeClass.Compact
    val useSideBySide = isExpandedWidth || isCompactHeight

    Scaffold { paddingValues ->
        val layoutDirection = LocalLayoutDirection.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = paddingValues.calculateStartPadding(layoutDirection),
                    end = if (useSideBySide) 12.dp else paddingValues.calculateEndPadding(layoutDirection)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            if (useSideBySide) {
                SideBySideLayout(isExpandedWidth, state, windowSizeClass)
            } else {
                StackLayout(state, windowSizeClass)
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
private fun StackLayout(state: HistoryModeBattleUIState, windowSizeClass: WindowSizeClass?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EnemySection(
            mobs = state.mobs,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.weight(1f)
        )

        CharSection(
            char = state.char,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun SideBySideLayout(
    isExpandedWidth: Boolean,
    state: HistoryModeBattleUIState,
    windowSizeClass: WindowSizeClass
) {
    val enemyWeight = getEnemyWeight(isExpandedWidth, state)

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        EnemySection(
            mobs = state.mobs,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.weight(enemyWeight),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.width(12.dp))

        CharSection(
            char = state.char,
            modifier = Modifier.weight(1f),
            alignment = Alignment.Center
        )
    }
}

private fun getEnemyWeight(
    isExpandedWidth: Boolean,
    state: HistoryModeBattleUIState
): Float {
    return if (isExpandedWidth) {
        when (state.mobs.size) {
            1 -> 1f
            2 -> 1.5f
            3 -> 2f
            else -> 2.5f
        }
    } else {
        1f
    }
}

@Composable
private fun EnemySection(
    mobs: List<BattleMobUIModel>,
    windowSizeClass: WindowSizeClass?,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp)
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
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = horizontalArrangement
    ) {
        items(mobs) { mob ->
            EnemyItem(
                mob = mob,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 12.dp)
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
                    .fillMaxSize()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                EnemyItem(
                    mob = mobs[page],
                    modifier = Modifier.fillMaxHeight()
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
    Box(
        modifier = modifier
            .aspectRatio(0.60f)
            .clip(RoundedCornerShape(4.dp))
            .border(
                width = 2.dp,
                color = CharacterBattleStrokeColor,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        BattleAsyncImage(
            model = mob.image,
            contentDescription = mob.name,
            modifier = Modifier.fillMaxSize()
        )

        EnemyInfo(mob)
    }
}

@Composable
private fun CharSection(
    char: BattleCharUIModel?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center
) {
    if (char == null) return

    Box(
        modifier = modifier
            .padding(vertical = 12.dp),
        contentAlignment = alignment
    ) {
        CharItem(
            char = char,
            modifier = Modifier.fillMaxHeight()
        )
    }
}

@Composable
private fun CharItem(
    char: BattleCharUIModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(0.60f)
            .clip(RoundedCornerShape(4.dp))
            .border(
                width = 2.dp,
                color = CharacterBattleStrokeColor,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        BattleAsyncImage(
            model = char.battleImage,
            contentDescription = char.name,
            modifier = Modifier.fillMaxSize()
        )

        CharInfo(char)
    }
}

@Composable
private fun BattleAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        filterQuality = FilterQuality.Medium,
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(0.6f, matchHeightConstraintsFirst = true),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Highlight,
                    strokeWidth = 2.dp
                )
            }
        },
    )
}

@Composable
private fun BoxScope.CharInfo(char: BattleCharUIModel) {
    Column(
        modifier = Modifier
            .matchParentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nível ${char.level}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            ),
            color = HighlightOnImage
        )

        Text(
            text = char.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            ),
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

@Composable
private fun BoxScope.EnemyInfo(mob: BattleMobUIModel) {
    Column(
        modifier = Modifier
            .matchParentSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nível ${mob.level}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            ),
            color = HighlightOnImage
        )

        Text(
            text = mob.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            ),
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

@Composable
private fun PulsingArrow(
    icon: ImageVector,
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "PulsingArrow")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Highlight,
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    alpha = alpha
                )
                .size(32.dp)
        )
    }
}

@Composable
private fun HealthBar(
    actualHealth: Long,
    totalHealth: Long,
    progress: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(HealthBarTrack),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .fillMaxHeight()
                .drawBehind {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                HealthBarRedEnd,
                                HealthBarRedStart
                            ),
                            startX = 0f,
                            endX = size.width / progress.coerceAtLeast(0.01f)
                        )
                    )
                }
                .align(Alignment.CenterStart)
        )

        Text(
            text = "$actualHealth / $totalHealth",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(1f, 1f),
                    blurRadius = 2f
                )
            ),
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}
