package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.CharPreviewData
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AttributeDecrementButton
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AttributeIncrementButton
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.animations.animateNumericStringProgressBarState

@Composable
internal fun CharAttributes(
    attributes: List<CharAttributesUIModel>,
    availablePoints: Long = 0,
    windowSizeClass: WindowSizeClass,
    onIncrementAttribute: (AttributeIdentifier) -> Unit = {},
    onDecrementAttribute: (AttributeIdentifier) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.char_attributes_title),
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif
            )
        )

        if (availablePoints > 0) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.char_available_points, availablePoints),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        val widthSizeClass = windowSizeClass.widthSizeClass
        val columns = if (widthSizeClass == WindowWidthSizeClass.Expanded) 2 else 1
        val itemsInLastRow = attributes.size % columns
        val spacersNeeded = if (itemsInLastRow > 0) columns - itemsInLastRow else 0

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            maxItemsInEachRow = columns,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            attributes.forEach { attribute ->
                key(attribute.identifier) {
                    AttributeItem(
                        attribute = attribute,
                        modifier = Modifier.weight(1f),
                        onIncrement = { onIncrementAttribute(attribute.identifier) },
                        onDecrement = { onDecrementAttribute(attribute.identifier) }
                    )
                }
            }

            repeat(spacersNeeded) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun AttributeItem(
    attribute: CharAttributesUIModel,
    modifier: Modifier = Modifier,
    onIncrement: () -> Unit = {},
    onDecrement: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AttributeTexts(attribute = attribute)

            AppProgressBar(progress = attribute.progress)
        }

        Spacer(modifier = Modifier.size(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AttributeDecrementButton(
                onClick = onDecrement,
                enabled = attribute.canDecrement
            )

            AttributeIncrementButton(
                onClick = onIncrement,
                enabled = attribute.canIncrement
            )
        }
    }
}

@Composable
private fun AttributeTexts(attribute: CharAttributesUIModel) {
    val displayValue = animateNumericStringProgressBarState(
        targetValue = attribute.totalValue,
        label = "AttributeValueAnimation"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(getAttributeLabel(attribute.identifier)),
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = FontFamily.Serif
            )
        )
        Text(
            text = displayValue.value,
            style = MaterialTheme.typography.labelLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = FontFamily.Serif
            )
        )
    }
}
private fun getAttributeLabel(identifier: AttributeIdentifier): Int {
    return when (identifier) {
        AttributeIdentifier.STRENGTH -> R.string.char_attribute_strength
        AttributeIdentifier.DEXTERITY -> R.string.char_attribute_dexterity
        AttributeIdentifier.INTELLIGENCE -> R.string.char_attribute_intelligence
        AttributeIdentifier.PHYSICAL_RESISTANCE -> R.string.char_attribute_physical_resistance
        AttributeIdentifier.MAGIC_RESISTANCE -> R.string.char_attribute_magic_resistance
        AttributeIdentifier.VITALITY -> R.string.char_attribute_vitality
        AttributeIdentifier.AGILITY -> R.string.char_attribute_agility
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    name = "Light Mode",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_NO,
    showBackground = true,
)
@Preview(
    name = "Dark Mode",
    device = Devices.PHONE,
    uiMode = UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Composable
private fun CharAttributesPreview() {
    val containerSize = LocalWindowInfo.current.containerSize
    val windowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(containerSize.width.dp, containerSize.height.dp),
    )

    EcosDoVazioTheme {
        CharAttributes(
            attributes = CharPreviewData.attributesInfo,
            availablePoints = 3,
            windowSizeClass = windowSizeClass,
        )
    }
}
