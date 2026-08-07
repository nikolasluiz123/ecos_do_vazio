package br.com.schmittsolucoes.ecosdovazio.presentation

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHeader
import br.com.schmittsolucoes.ecosdovazio.presentation.chars.selection.navigation.CharSelectionRoute

data class AppUIState(
    val isInitializing: Boolean = true,
    val startDestination: Any = CharSelectionRoute,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val loadingMessage: String? = null,
    val snackbarMessage: String? = null,
    val charHeader: CharHeader? = null,
    val profileImageRes: Int? = null
)