package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeUIState

private const val COMPACT_COLUMNS = 1
private const val MEDIUM_COLUMNS = 2
private const val EXPANDED_COLUMNS = 3
private const val DEFAULT_COLUMNS = 1

@Composable
internal fun BannersContainer(
    state: HomeUIState,
    windowSizeClass: WindowSizeClass?,
    onNavigateToSpecializationSelection: () -> Unit,
    onNavigateToBattle: (String) -> Unit,
    onNavigateToMobsInfo: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val maxColumns = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> COMPACT_COLUMNS
        WindowWidthSizeClass.Medium -> MEDIUM_COLUMNS
        WindowWidthSizeClass.Expanded -> EXPANDED_COLUMNS
        else -> DEFAULT_COLUMNS
    }

    val banners = buildList<@Composable (Modifier) -> Unit> {
        if (state.showSpecializationBanner) {
            add { modifier ->
                SpecializationBanner(
                    onClickSelectSpecialization = onNavigateToSpecializationSelection,
                    modifier = modifier,
                )
            }
        }

        state.lastUnfinishedHistoryPhase?.let { lastUnfinishedHistoryPhase ->
            add { modifier ->
                HistoryBanner(
                    model = lastUnfinishedHistoryPhase,
                    onNavigateToBattle = onNavigateToBattle,
                    onNavigateToMobsInfo = onNavigateToMobsInfo,
                    modifier = modifier,
                )
            }
        }
    }

    if (banners.isEmpty()) return

    val effectiveColumns = minOf(banners.size, maxColumns)

    if (effectiveColumns <= 1) {
        CommonColumn(banners = banners, modifier = modifier)
    } else {
        ChunkedColumn(banners = banners, columns = effectiveColumns, modifier = modifier)
    }
}

@Composable
private fun ChunkedColumn(
    banners: List<@Composable (Modifier) -> Unit>,
    columns: Int,
    modifier: Modifier = Modifier,
) {
    val rows = banners.chunked(columns)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        rows.forEach { rowBanners ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            ) {
                rowBanners.forEach { banner ->
                    banner(
                        Modifier
                            .weight(1f, fill = false)
                            .fillMaxHeight(),
                    )
                }
            }
        }
    }
}

@Composable
private fun CommonColumn(
    banners: List<@Composable (Modifier) -> Unit>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        banners.forEach { banner ->
            banner(Modifier)
        }
    }
}
