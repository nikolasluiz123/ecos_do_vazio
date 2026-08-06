package br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.CharSelectionViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables.components.HeroSelectionDivider
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.composables.components.HeroSlot
import br.com.schmittsolucoes.ecosdovazio.presentation.components.ErrorDialog
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.EcosDoVazioTheme
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.OrangeForDetails
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.PrimaryTextColor
import br.com.schmittsolucoes.ecosdovazio.presentation.theme.SecondaryTextColor

@Composable
fun CharSelectionScreen(
    viewModel: CharSelectionViewModel,
    windowWidthSizeClass: WindowWidthSizeClass,
    onNavigateToClassSelection: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharSelectionScreen(
        state = state,
        windowWidthSizeClass = windowWidthSizeClass,
        onNavigateToClassSelection = onNavigateToClassSelection,
        onDismissErrorDialog = viewModel::onDismissErrorDialog
    )
}

@Composable
fun CharSelectionScreen(
    state: CharSelectionUIState = CharSelectionUIState(),
    windowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    onNavigateToClassSelection: () -> Unit = {},
    onDismissErrorDialog: () -> Unit = {}
) {
    val columns = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> 2
        WindowWidthSizeClass.Medium -> 3
        else -> 3
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            OrangeForDetails.copy(alpha = 0.06f),
                            OrangeForDetails.copy(alpha = 0.05f),
                            OrangeForDetails.copy(alpha = 0.04f),
                            OrangeForDetails.copy(alpha = 0.03f),
                            Color.Transparent
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(18.dp))

                        Text(
                            text = stringResource(R.string.my_heroes_title),
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryTextColor
                            )
                        )

                        HeroSelectionDivider(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .width(180.dp)
                        )

                        Text(
                            text = stringResource(R.string.heroes_selection_text),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 40.dp),
                            color = SecondaryTextColor
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                    }
                }

                items(state.chars) { charModel ->
                    HeroSlot(
                        charModel = charModel,
                        onClick = {
                            if (it.id == null) {
                                onNavigateToClassSelection()
                            }
                        }
                    )
                }
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
fun CharSelectionScreenPreviewLight() {
    EcosDoVazioTheme(darkTheme = false) {
        CharSelectionScreen(
            state = CharSelectionUIState()
        )
    }
}

@Preview(name = "Dark Mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun CharSelectionScreenPreviewDark() {
    EcosDoVazioTheme(darkTheme = true) {
        CharSelectionScreen(
            state = CharSelectionUIState()
        )
    }
}