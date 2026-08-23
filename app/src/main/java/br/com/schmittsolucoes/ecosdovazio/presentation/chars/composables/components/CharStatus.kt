package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroButtonStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight

@Composable
internal fun CharStatus(
    statusInfo: CharStatusUIModel,
    windowSizeClass: WindowSizeClass
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.char_status_title),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        val widthSizeClass = windowSizeClass.widthSizeClass

        val statusItems = remember(statusInfo) {
            listOf(
                R.string.char_status_hp to statusInfo.hp,
                R.string.char_status_damage to statusInfo.baseDamage,
                R.string.char_status_phys_res to statusInfo.physicalResistance,
                R.string.char_status_mag_res to statusInfo.magicResistance,
                R.string.char_status_crit to statusInfo.criticalChance,
                R.string.char_status_dodge to statusInfo.dodgeChance,
            )
        }

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, HeroButtonStrokeColor, RoundedCornerShape(8.dp))
                .padding(16.dp),
            maxItemsInEachRow = getMaxItemsEachRow(widthSizeClass),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            statusItems.forEach { (labelRes, value) ->
                StatusItem(
                    label = stringResource(labelRes),
                    value = value,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private fun getMaxItemsEachRow(widthSizeClass: WindowWidthSizeClass): Int {
    return when (widthSizeClass) {
        WindowWidthSizeClass.Compact -> 2
        else -> 3
    }
}

@Composable
private fun StatusItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Highlight,
            )
        )
    }
}
