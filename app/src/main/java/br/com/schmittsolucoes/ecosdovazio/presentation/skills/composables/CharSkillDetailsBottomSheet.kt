package br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components.AttributeProgressBar
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AttributeDecrementButton
import br.com.schmittsolucoes.ecosdovazio.presentation.components.AttributeIncrementButton
import br.com.schmittsolucoes.ecosdovazio.presentation.components.CustomSectionDivider
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharSkillDetailsBottomSheet(
    skill: CharSkillDetailsUIModel,
    attributes: List<CharAttributesUIModel>,
    availablePoints: Long,
    onDismissRequest: () -> Unit,
    onIncrementAttribute: (AttributeIdentifier) -> Unit,
    onDecrementAttribute: (AttributeIdentifier) -> Unit,
) {
    val scrollState = rememberScrollState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier.navigationBarsPadding(),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(18.dp))
                CustomSectionDivider(
                    modifier = Modifier.width(120.dp)
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = skill.name,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = Highlight,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = skill.description.removeDamageFormula(),
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            SkillInfoGrid(skill = skill)

            Spacer(modifier = Modifier.height(32.dp))

            if (availablePoints > 0) {
                Text(
                    text = stringResource(R.string.char_available_points, availablePoints),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Highlight,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily.Serif
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            attributes.forEach { attribute ->
                SkillAttributeAdjustmentItem(
                    attribute = attribute,
                    canIncrement = attribute.canIncrement,
                    canDecrement = attribute.canDecrement,
                    onIncrement = { onIncrementAttribute(attribute.identifier) },
                    onDecrement = { onDecrementAttribute(attribute.identifier) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SkillInfoGrid(skill: CharSkillDetailsUIModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        SkillInfo(
            label = stringResource(
                R.string.skill_tooltip_min_level,
                skill.minLevel,
            ),
        )
    }
}

@Composable
private fun SkillInfo(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun SkillAttributeAdjustmentItem(
    attribute: CharAttributesUIModel,
    canIncrement: Boolean,
    canDecrement: Boolean,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
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

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AttributeDecrementButton(
                onClick = onDecrement,
                enabled = canDecrement
            )

            AttributeIncrementButton(
                onClick = onIncrement,
                enabled = canIncrement
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

private fun String.removeDamageFormula(): String {
    return this.replace(Regex("""\s*\(.*?\)"""), "")
}
