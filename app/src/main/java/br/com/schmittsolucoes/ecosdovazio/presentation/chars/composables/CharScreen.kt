package br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.CharViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.composables.components.AttributeProgressBar
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharAttributesUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharLevelInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.model.CharStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.HeroButtonStrokeColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight

@Composable
fun CharScreen(
    viewModel: CharViewModel,
    windowSizeClass: WindowSizeClass
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onIncrementAttribute = viewModel::onIncrementAttribute
    )
}

@Composable
fun CharScreen(
    state: CharUIState = CharUIState(),
    windowSizeClass: WindowSizeClass,
    onDismissErrorDialog: () -> Unit = {},
    onIncrementAttribute: (CharAttributes.AttributeIdentifier) -> Unit = {}
) {
    Scaffold { paddingValues ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
                .verticalScroll(scrollState)
                .padding(16.dp),
        ) {

            state.levelInfo?.let { levelInfo ->
                CharLevelInfo(levelInfo = levelInfo)
            }

            Spacer(modifier = Modifier.height(24.dp))

            state.statusInfo?.let { statusInfo ->
                CharStatus(
                    statusInfo = statusInfo,
                    windowSizeClass = windowSizeClass
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            state.attributesInfo?.let { attributesInfo ->
                CharAttributes(
                    attributes = attributesInfo,
                    availablePoints = state.availablePoints,
                    onIncrementAttribute = onIncrementAttribute
                )
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
fun CharLevelInfo(
    levelInfo: CharLevelInfoUIModel
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.char_level_label, levelInfo.level),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Serif
                )
            )
            Text(
                text = stringResource(
                    R.string.char_xp_label,
                    levelInfo.currentExperience,
                    levelInfo.nextLevelExperience
                ),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = FontFamily.Serif
                )
            )
        }
        AttributeProgressBar(
            progress = { levelInfo.progress }
        )
    }
}

@Composable
fun CharStatus(
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
fun CharAttributes(
    attributes: List<CharAttributesUIModel>,
    availablePoints: Long = 0,
    onIncrementAttribute: (CharAttributes.AttributeIdentifier) -> Unit = {}
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            attributes.forEach { attribute ->
                AttributeItem(
                    attribute = attribute,
                    showIncrementButton = availablePoints > 0,
                    onIncrement = { onIncrementAttribute(attribute.identifier) }
                )
            }
        }
    }
}

@Composable
private fun AttributeItem(
    attribute: CharAttributesUIModel,
    showIncrementButton: Boolean = false,
    onIncrement: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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

        if (showIncrementButton) {
            Spacer(modifier = Modifier.size(8.dp))

            SmallFloatingActionButton(onClick = onIncrement) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    }
}

private fun getAttributeLabel(identifier: CharAttributes.AttributeIdentifier): Int {
    return when (identifier) {
        CharAttributes.AttributeIdentifier.STRENGTH -> R.string.char_attribute_strength
        CharAttributes.AttributeIdentifier.DEXTERITY -> R.string.char_attribute_dexterity
        CharAttributes.AttributeIdentifier.INTELLIGENCE -> R.string.char_attribute_intelligence
        CharAttributes.AttributeIdentifier.PHYSICAL_RESISTANCE -> R.string.char_attribute_physical_resistance
        CharAttributes.AttributeIdentifier.MAGIC_RESISTANCE -> R.string.char_attribute_magic_resistance
        CharAttributes.AttributeIdentifier.VITALITY -> R.string.char_attribute_vitality
        CharAttributes.AttributeIdentifier.AGILITY -> R.string.char_attribute_agility
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
