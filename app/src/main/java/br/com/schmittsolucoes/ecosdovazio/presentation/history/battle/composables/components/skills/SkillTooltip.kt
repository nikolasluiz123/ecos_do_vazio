package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables.components.skills

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

@Composable
internal fun SkillTooltip(
    skill: CharSkillUIModel,
    onDismissRequest: () -> Unit,
) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true)
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 300.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            tonalElevation = 8.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = skill.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismissRequest, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.skill_tooltip_close)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = skill.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                SkillInfoRow(stringResource(R.string.skill_tooltip_min_level, skill.minLevel))

                if (skill.attributes.requiredStrength > 0) {
                    SkillInfoRow(stringResource(R.string.skill_tooltip_required_strength, skill.attributes.requiredStrength))
                }
                if (skill.attributes.requiredDexterity > 0) {
                    SkillInfoRow(stringResource(R.string.skill_tooltip_required_dexterity, skill.attributes.requiredDexterity))
                }
                if (skill.attributes.requiredIntelligence > 0) {
                    SkillInfoRow(stringResource(R.string.skill_tooltip_required_intelligence, skill.attributes.requiredIntelligence))
                }
                if (skill.attributes.requiredPhysicalResistance > 0) {
                    SkillInfoRow(stringResource(R.string.skill_tooltip_required_physical_resistance, skill.attributes.requiredPhysicalResistance))
                }
                if (skill.attributes.requiredMagicResistance > 0) {
                    SkillInfoRow(stringResource(R.string.skill_tooltip_required_magic_resistance, skill.attributes.requiredMagicResistance))
                }
                if (skill.attributes.requiredVitality > 0) {
                    SkillInfoRow(stringResource(R.string.skill_tooltip_required_vitality, skill.attributes.requiredVitality))
                }
                if (skill.attributes.requiredAgility > 0) {
                    SkillInfoRow(stringResource(R.string.skill_tooltip_required_agility, skill.attributes.requiredAgility))
                }
            }
        }
    }
}

@Composable
private fun SkillInfoRow(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
        modifier = Modifier.padding(vertical = 2.dp)
    )
}
