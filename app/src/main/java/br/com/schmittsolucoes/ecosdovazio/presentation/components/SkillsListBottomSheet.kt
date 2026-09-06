package br.com.schmittsolucoes.ecosdovazio.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.composables.SkillInfoItem
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.Highlight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsListBottomSheet(
    title: String,
    skills: List<CharSkillUIModel>,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Highlight,
                textAlign = TextAlign.Center
            )
        )

        CustomSectionDivider(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        )

        skills.forEach { skill ->
            SkillInfoItem(
                drawableRes = skill.image,
                name = skill.name,
                description = skill.description,
                blocked = skill.blocked,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
