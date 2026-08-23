package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components

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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AttributeDecrementButton
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AttributeIncrementButton

@Composable
internal fun CharAttributes(
    attributes: List<CharAttributesUIModel>,
    availablePoints: Long = 0,
    windowSizeClass: WindowSizeClass,
    onIncrementAttribute: (AttributeIdentifier) -> Unit = {},
    onDecrementAttribute: (AttributeIdentifier) -> Unit = {}
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
                AttributeItem(
                    attribute = attribute,
                    modifier = Modifier.weight(1f),
                    onIncrement = { onIncrementAttribute(attribute.identifier) },
                    onDecrement = { onDecrementAttribute(attribute.identifier) }
                )
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
                    text = attribute.totalValue,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontFamily = FontFamily.Serif
                    )
                )
            }

            AttributeProgressBar(progress = { attribute.progress })
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
