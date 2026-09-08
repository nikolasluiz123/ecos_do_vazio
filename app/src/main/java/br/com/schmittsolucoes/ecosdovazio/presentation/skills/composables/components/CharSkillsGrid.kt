package br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables.CharSkillItem
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables.CharSkillItemLoading
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables.CharSkillsPreviewData
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

private val GridContentPadding = 16.dp
private val GridVerticalSpacing = 32.dp
private val GridHorizontalSpacing = 16.dp

private const val COMPACT_COLUMNS = 2
private const val MEDIUM_COLUMNS = 3
private const val EXPANDED_COLUMNS = 5
private const val DEFAULT_COLUMNS = 1
private const val DEFAULT_LOADING_ITEM_COUNT = 20

@Composable
fun CharSkillsGrid(
    skills: List<CharSkillDetailsUIModel>,
    onSelectSkill: (CharSkillDetailsUIModel) -> Unit,
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass? = null,
    isLoading: Boolean = false,
) {
    val columns = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> COMPACT_COLUMNS
        WindowWidthSizeClass.Medium -> MEDIUM_COLUMNS
        WindowWidthSizeClass.Expanded -> EXPANDED_COLUMNS
        else -> DEFAULT_COLUMNS
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(GridContentPadding),
        verticalArrangement = Arrangement.spacedBy(GridVerticalSpacing),
        horizontalArrangement = Arrangement.spacedBy(GridHorizontalSpacing),
        modifier = modifier.fillMaxSize(),
    ) {
        if (isLoading || skills.isEmpty()) {
            items(DEFAULT_LOADING_ITEM_COUNT) {
                CharSkillItemLoading()
            }
        } else {
            items(skills) { skill ->
                CharSkillItem(
                    skill = skill,
                    onClick = { onSelectSkill(skill) },
                )
            }
        }
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun CharSkillsGridPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        Box(modifier = Modifier.background(BackgroundGradient)) {
            CharSkillsGrid(
                skills = CharSkillsPreviewData.skillList,
                onSelectSkill = {},
            )
        }
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun CharSkillsGridPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        Box(modifier = Modifier.background(BackgroundGradient)) {
            CharSkillsGrid(
                skills = CharSkillsPreviewData.skillList,
                onSelectSkill = {},
            )
        }
    }
}
