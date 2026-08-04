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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.ClassSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.ClassSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components.ClassList
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.composables.components.ClassPager
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails

@Composable
fun ClassSelectionScreen(
    viewModel: ClassSelectionViewModel,
    windowWidthSizeClass: WindowWidthSizeClass
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ClassSelectionScreen(
        state = state,
        windowWidthSizeClass = windowWidthSizeClass,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun ClassSelectionScreen(
    state: ClassSelectionUIState = ClassSelectionUIState(),
    windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onDismissErrorDialog: () -> Unit = {},
    onSelectClass: (String) -> Unit = {}
) {
    val isCompact = windowWidthSizeClass == WindowWidthSizeClass.Compact

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            OrangeForDetails.copy(alpha = 0.08f),
                            OrangeForDetails.copy(alpha = 0.06f),
                            OrangeForDetails.copy(alpha = 0.04f),
                            Color.Transparent
                        )
                    )
                )
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (isCompact) {
                ClassPager(
                    classes = state.classes,
                    onSelectClass = onSelectClass,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                ClassList(
                    classes = state.classes,
                    onSelectClass = onSelectClass,
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
}

@Preview(name = "Light Mode", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ClassSelectionScreenPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        ClassSelectionScreen(
            state = ClassSelectionUIState(
                classes = listOf(
                    br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel(
                        id = "1",
                        name = "Guerreiro",
                        description = "Especialista em combate corpo a corpo, atua na linha de frente equipado com armaduras pesadas.",
                        presentationDrawableId = android.R.drawable.ic_menu_gallery
                    ),
                    br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel(
                        id = "2",
                        name = "Mago",
                        description = "Mestre em feitiços e ataques à distância, veste armaduras leves de tecido.",
                        presentationDrawableId = android.R.drawable.ic_menu_gallery
                    )
                )
            )
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ClassSelectionScreenPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        ClassSelectionScreen(
            state = ClassSelectionUIState(
                classes = listOf(
                    br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel(
                        id = "1",
                        name = "Guerreiro",
                        description = "Especialista em combate corpo a corpo, atua na linha de frente equipado com armaduras pesadas.",
                        presentationDrawableId = android.R.drawable.ic_menu_gallery
                    )
                )
            )
        )
    }
}
