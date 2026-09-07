package br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.ClassSelectionNavigationEvent
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.ClassSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.ClassSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components.CharNamingBottomSheet
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ObserveAsEvents
import br.com.schmittsolucoes.ecosdovazio.presentation.components.SelectionList
import br.com.schmittsolucoes.ecosdovazio.presentation.components.SelectionPager
import br.com.schmittsolucoes.ecosdovazio.presentation.components.SkillsListBottomSheet
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.BackgroundGradient
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme

@Composable
fun ClassSelectionScreen(
    viewModel: ClassSelectionViewModel,
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToHome: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.navigationEvent) { event ->
        when (event) {
            ClassSelectionNavigationEvent.NavigateToHome -> onNavigateToHome()
        }
    }

    ClassSelectionScreen(
        state = state,
        windowWidthSizeClass = windowWidthSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog,
        onSelectClass = viewModel::onSelectClass,
        onConfirmName = viewModel::onConfirmName,
        onCardClick = viewModel::onClassCardClick,
        onDismissSkillsBottomSheet = viewModel::onDismissSkillsBottomSheet,
    )
}

@Composable
fun ClassSelectionScreen(
    state: ClassSelectionUIState = ClassSelectionUIState(),
    windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onDismissErrorDialog: () -> Unit = {},
    onSelectClass: (String) -> Unit = {},
    onConfirmName: (String) -> Unit = {},
    onCardClick: (SelectionItemUIModel) -> Unit = {},
    onDismissSkillsBottomSheet: () -> Unit = {},
) {
    var showNamingBottomSheet by remember { mutableStateOf(value = false) }

    val isCompact = windowWidthSizeClass == WindowWidthSizeClass.Compact

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGradient)
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            if (isCompact) {
                SelectionPager(
                    items = state.classes,
                    onSelectItem = {
                        onSelectClass(it)
                        showNamingBottomSheet = true
                    },
                    onCardClick = onCardClick,
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                SelectionList(
                    items = state.classes,
                    onSelectItem = {
                        onSelectClass(it)
                        showNamingBottomSheet = true
                    },
                    onCardClick = onCardClick,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            state.errorMessage?.let { message ->
                ErrorDialog(
                    message = message,
                    onDismiss = onDismissErrorDialog,
                )
            }
        }
    }

    if (showNamingBottomSheet) {
        CharNamingBottomSheet(
            onDismissRequest = { showNamingBottomSheet = false },
            onConfirm = { name ->
                onConfirmName(name)
                showNamingBottomSheet = false
            },
        )
    }

    state.selectedClassSkills?.let { skills ->
        SkillsListBottomSheet(
            title = state.selectedClassName.orEmpty(),
            skills = skills,
            onDismissRequest = onDismissSkillsBottomSheet,
        )
    }
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ClassSelectionScreenPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        ClassSelectionScreen(
            state = ClassSelectionPreviewData.uiStateLoaded,
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ClassSelectionScreenPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        ClassSelectionScreen(
            state = ClassSelectionPreviewData.uiStateLoaded,
        )
    }
}
