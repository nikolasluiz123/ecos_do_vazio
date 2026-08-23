package br.com.schmittsolucoes.ecosdovazio.presentation.history.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient

private val GridContentPadding = 16.dp
private val GridVerticalSpacing = 32.dp
private val GridHorizontalSpacing = 16.dp

private const val COMPACT_COLUMNS = 1
private const val MEDIUM_COLUMNS = 2
private const val EXPANDED_COLUMNS = 3
private const val DEFAULT_COLUMNS = 1

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    windowSizeClass: WindowSizeClass,
    onPhaseClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    HistoryScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onPhaseClick = onPhaseClick
    )
}

@Composable
fun HistoryScreen(
    state: HistoryUIState = HistoryUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {},
    onPhaseClick: (String) -> Unit = {}
) {
    val columns = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> COMPACT_COLUMNS
        WindowWidthSizeClass.Medium -> MEDIUM_COLUMNS
        WindowWidthSizeClass.Expanded -> EXPANDED_COLUMNS
        else -> DEFAULT_COLUMNS
    }

    val gridState = rememberLazyGridState(initialFirstVisibleItemIndex = state.actualPhaseIndex)

    LaunchedEffect(state.actualPhaseIndex, state.phases.isNotEmpty()) {
        if (state.phases.isNotEmpty() && state.actualPhaseIndex in state.phases.indices) {
            gridState.animateScrollToItem(state.actualPhaseIndex)
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(GridContentPadding),
                verticalArrangement = Arrangement.spacedBy(GridVerticalSpacing),
                horizontalArrangement = Arrangement.spacedBy(GridHorizontalSpacing),
                state = gridState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.phases) { phase ->
                    HistoryPhaseItem(
                        phase = phase,
                        onPhaseClick = onPhaseClick
                    )
                }
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

