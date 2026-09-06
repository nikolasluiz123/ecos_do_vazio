package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.composables.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.components.CustomSectionDivider
import br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.SkillInfoItem
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseMobInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.PhaseCardBorderColor

private val CARD_CORNER_RADIUS = 8.dp

@Composable
internal fun HistoryPhaseMobInfoItem(
    mobInfo: HistoryPhaseMobInfoUIModel,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
        border = BorderStroke(1.dp, PhaseCardBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            MobPhaseInfoHeader(mobPhaseInfo = mobInfo.mobPhaseInfo)

            if (mobInfo.skills.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                CustomSectionDivider(modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    mobInfo.skills.forEach { skill ->
                        SkillInfoItem(
                            drawableRes = skill.image,
                            name = skill.name,
                            description = skill.description,
                            blocked = skill.blocked,
                        )
                    }
                }
            }
        }
    }
}
