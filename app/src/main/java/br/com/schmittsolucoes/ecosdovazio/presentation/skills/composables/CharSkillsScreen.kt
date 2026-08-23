package br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.CharSkillsUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.CharSkillsViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.model.CharSkillDetailsUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient

private val GridContentPadding = 16.dp
private val GridVerticalSpacing = 32.dp
private val GridHorizontalSpacing = 16.dp

private const val COMPACT_COLUMNS = 2
private const val MEDIUM_COLUMNS = 3
private const val EXPANDED_COLUMNS = 5
private const val DEFAULT_COLUMNS = 1

@Composable
fun CharSkillsScreen(
    viewModel: CharSkillsViewModel,
    windowSizeClass: WindowSizeClass
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharSkillsScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onSelectSkill = viewModel::onSelectSkill,
        onDismissSkillDetails = viewModel::onDismissSkillDetails,
        onIncrementAttribute = viewModel::onIncrementAttribute,
        onDecrementAttribute = viewModel::onDecrementAttribute
    )
}

@Composable
fun CharSkillsScreen(
    state: CharSkillsUIState = CharSkillsUIState(),
    windowSizeClass: WindowSizeClass? = null,
    onDismissErrorDialog: () -> Unit = {},
    onSelectSkill: (CharSkillDetailsUIModel) -> Unit = {},
    onDismissSkillDetails: () -> Unit = {},
    onIncrementAttribute: (AttributeIdentifier) -> Unit = {},
    onDecrementAttribute: (AttributeIdentifier) -> Unit = {}
) {
    val columns = when (windowSizeClass?.widthSizeClass) {
        WindowWidthSizeClass.Compact -> COMPACT_COLUMNS
        WindowWidthSizeClass.Medium -> MEDIUM_COLUMNS
        WindowWidthSizeClass.Expanded -> EXPANDED_COLUMNS
        else -> DEFAULT_COLUMNS
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(BackgroundGradient)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(GridContentPadding),
                verticalArrangement = Arrangement.spacedBy(GridVerticalSpacing),
                horizontalArrangement = Arrangement.spacedBy(GridHorizontalSpacing),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.skills) { skill ->
                    CharSkillItem(
                        skill = skill,
                        onClick = { onSelectSkill(skill) }
                    )
                }
            }

            state.selectedSkill?.let { skill ->
                CharSkillDetailsBottomSheet(
                    skill = skill,
                    attributes = state.selectedSkillAttributes,
                    availablePoints = state.availablePoints,
                    onDismissRequest = onDismissSkillDetails,
                    onIncrementAttribute = onIncrementAttribute,
                    onDecrementAttribute = onDecrementAttribute
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
