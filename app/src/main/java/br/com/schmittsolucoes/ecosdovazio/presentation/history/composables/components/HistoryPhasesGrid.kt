package br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryPhaseUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.HistoryPhaseItem
import br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.HistoryPhaseItemLoading
import br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.HistoryPreviewData
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

private val GridContentPadding = 16.dp
private val GridVerticalSpacing = 32.dp
private val GridHorizontalSpacing = 16.dp

private const val COMPACT_COLUMNS = 1
private const val MEDIUM_COLUMNS = 2
private const val EXPANDED_COLUMNS = 3
private const val DEFAULT_COLUMNS = 1
private const val DEFAULT_LOADING_ITEM_COUNT = 20

@Composable
fun HistoryPhasesGrid(
    phases: List<HistoryPhaseUIModel>,
    actualPhaseIndex: Int,
    onPhaseClick: (String) -> Unit,
    onInfoClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass? = null,
    isLoading: Boolean = false,
) {
    val columns = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> COMPACT_COLUMNS
        WindowWidthSizeClass.Medium -> MEDIUM_COLUMNS
        WindowWidthSizeClass.Expanded -> EXPANDED_COLUMNS
        else -> DEFAULT_COLUMNS
    }

    val gridState = rememberLazyGridState(initialFirstVisibleItemIndex = actualPhaseIndex)

    LaunchedEffect(actualPhaseIndex, phases.isNotEmpty()) {
        if (phases.isNotEmpty() && (actualPhaseIndex in phases.indices)) {
            gridState.animateScrollToItem(actualPhaseIndex)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(GridContentPadding),
        verticalArrangement = Arrangement.spacedBy(GridVerticalSpacing),
        horizontalArrangement = Arrangement.spacedBy(GridHorizontalSpacing),
        state = gridState,
        modifier = modifier.fillMaxSize(),
    ) {
        if (isLoading || phases.isEmpty()) {
            items(DEFAULT_LOADING_ITEM_COUNT) {
                HistoryPhaseItemLoading()
            }
        } else {
            items(phases) { phase ->
                HistoryPhaseItem(
                    phase = phase,
                    onPhaseClick = onPhaseClick,
                    onInfoClick = onInfoClick,
                )
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HistoryPhasesGridPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        Box(modifier = Modifier.background(BackgroundGradient)) {
            HistoryPhasesGrid(
                phases = HistoryPreviewData.phaseList,
                actualPhaseIndex = 1,
                onPhaseClick = {},
                onInfoClick = {},
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun HistoryPhasesGridPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        Box(modifier = Modifier.background(BackgroundGradient)) {
            HistoryPhasesGrid(
                phases = HistoryPreviewData.phaseList,
                actualPhaseIndex = 1,
                onPhaseClick = {},
                onInfoClick = {},
            )
        }
    }
}
