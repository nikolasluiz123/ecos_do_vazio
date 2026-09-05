package br.com.schmittsolucoes.ecosdovazio.presentation.home

data class HomeUIState(
    val showSpecializationBanner: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
