package br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.R.drawable
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.components.SelectionList
import br.com.schmittsolucoes.ecosdovazio.presentation.components.SelectionPager
import br.com.schmittsolucoes.ecosdovazio.presentation.components.SkillsListBottomSheet
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection.SpecializationSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.specialization.selection.SpecializationSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun SpecializationSelectionScreen(
    viewModel: SpecializationSelectionViewModel,
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToHome: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navigateToHome by viewModel.navigateToHome.collectAsStateWithLifecycle()

    LaunchedEffect(navigateToHome) {
        if (navigateToHome) {
            onNavigateToHome()
            viewModel.onNavigatedToHome()
        }
    }

    SpecializationSelectionScreen(
        state = state,
        windowWidthSizeClass = windowWidthSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onSelectSpecialization = viewModel::onSelectSpecialization,
        onCardClick = viewModel::onSpecializationCardClick,
        onDismissSkillsBottomSheet = viewModel::onDismissSkillsBottomSheet
    )
}

@Composable
fun SpecializationSelectionScreen(
    state: SpecializationSelectionUIState = SpecializationSelectionUIState(),
    windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onDismissErrorDialog: () -> Unit = {},
    onSelectSpecialization: (String) -> Unit = {},
    onCardClick: (SelectionItemUIModel) -> Unit = {},
    onDismissSkillsBottomSheet: () -> Unit = {}
) {
    val isCompact = windowWidthSizeClass == WindowWidthSizeClass.Compact

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isCompact) {
                SelectionPager(
                    items = state.specializations,
                    onSelectItem = onSelectSpecialization,
                    onCardClick = onCardClick,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                SelectionList(
                    items = state.specializations,
                    onSelectItem = onSelectSpecialization,
                    onCardClick = onCardClick,
                    modifier = Modifier.fillMaxSize()
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

    state.selectedSpecializationSkills?.let { skills ->
        SkillsListBottomSheet(
            title = state.selectedSpecializationName.orEmpty(),
            skills = skills,
            onDismissRequest = onDismissSkillsBottomSheet
        )
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun SpecializationSelectionScreenPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        SpecializationSelectionScreen(
            state = SpecializationSelectionUIState(
                specializations = listOf(
                    SelectionItemUIModel(
                        id = "1",
                        name = "Guardião",
                        description = "Foco em defesa e buffs. Incrementa Resistência Física, Resistência Mágica e Vitalidade.",
                        presentationDrawableId = drawable.especializacao_guardiao_16_9
                    ),
                    SelectionItemUIModel(
                        id = "2",
                        name = "Gladiador",
                        description = "Foco em dano e debuffs. Incrementa Força e Vitalidade.",
                        presentationDrawableId = drawable.especializacao_gladiador_16_9
                    )
                )
            )
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SpecializationSelectionScreenPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        SpecializationSelectionScreen(
            state = SpecializationSelectionUIState(
                specializations = listOf(
                    SelectionItemUIModel(
                        id = "1",
                        name = "Guardião",
                        description = "Foco em defesa e buffs. Incrementa Resistência Física, Resistência Mágica e Vitalidade.",
                        presentationDrawableId = drawable.especializacao_guardiao_16_9
                    )
                )
            )
        )
    }
}
