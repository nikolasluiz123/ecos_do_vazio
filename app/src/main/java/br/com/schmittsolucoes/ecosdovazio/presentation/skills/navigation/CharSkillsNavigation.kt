package br.com.schmittsolucoes.ecosdovazio.presentation.skills.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.CharSkillsViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.skills.composables.CharSkillsScreen

fun NavController.navigateToCharSkills() {
    navigate(CharSkillsRoute)
}

fun NavGraphBuilder.charSkillsScreen(
    windowSizeClass: WindowSizeClass
) {
    composable<CharSkillsRoute> {
        val viewModel = hiltViewModel<CharSkillsViewModel>()
        CharSkillsScreen(
            viewModel = viewModel,
            windowSizeClass = windowSizeClass
        )
    }
}
