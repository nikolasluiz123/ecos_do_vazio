package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseMobInfoUIModel

private const val COMPACT_COLUMNS = 1
private const val MEDIUM_COLUMNS = 1
private const val EXPANDED_COLUMNS = 2
private const val DEFAULT_COLUMNS = 1

private val ListContentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
private val ListVerticalArrangement = Arrangement.spacedBy(16.dp)

@Composable
internal fun HistoryPhaseMobsList(
    mobsInfo: List<HistoryPhaseMobInfoUIModel>,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass? = null,
) {
    val columns = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> COMPACT_COLUMNS
        WindowWidthSizeClass.Medium -> MEDIUM_COLUMNS
        WindowWidthSizeClass.Expanded -> EXPANDED_COLUMNS
        else -> DEFAULT_COLUMNS
    }

    if (columns <= 1) {
        CommonColumn(modifier, mobsInfo)
    } else {
        ChunkedColumn(mobsInfo, columns, modifier)
    }
}

@Composable
private fun ChunkedColumn(
    mobsInfo: List<HistoryPhaseMobInfoUIModel>,
    columns: Int,
    modifier: Modifier = Modifier,
) {
    val rows = mobsInfo.chunked(columns)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(ListContentPadding),
        verticalArrangement = ListVerticalArrangement,
    ) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                rowItems.forEach { mobInfo ->
                    HistoryPhaseMobInfoItem(
                        mobInfo = mobInfo,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    )
                }

                repeat(columns - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun CommonColumn(
    modifier: Modifier = Modifier,
    mobsInfo: List<HistoryPhaseMobInfoUIModel>,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(ListContentPadding),
        verticalArrangement = ListVerticalArrangement,
    ) {
        mobsInfo.forEach { mobInfo ->
            HistoryPhaseMobInfoItem(mobInfo = mobInfo)
        }
    }
}
