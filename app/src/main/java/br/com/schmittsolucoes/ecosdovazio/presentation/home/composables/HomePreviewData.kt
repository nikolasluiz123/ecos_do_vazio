package br.com.schmittsolucoes.ecosdovazio.presentation.home.composables

import br.com.schmittsolucoes.ecosdovazio.presentation.history.model.LastUnfinishedHistoryPhaseUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.home.HomeUIState

object HomePreviewData {

    val unfinishedPhase = LastUnfinishedHistoryPhaseUIModel(
        phaseId = "1",
        phaseName = "Espada Lascada",
        completedPhasesCount = 2,
        totalPhasesCount = 10,
        progress = 0.2f,
        isCompleted = false
    )

    val completedPhase = LastUnfinishedHistoryPhaseUIModel(
        phaseId = null,
        phaseName = "",
        completedPhasesCount = 10,
        totalPhasesCount = 10,
        progress = 1f,
        isCompleted = true
    )

    val homeStateWithAllBanners = HomeUIState(
        showSpecializationBanner = true,
        lastUnfinishedHistoryPhase = unfinishedPhase
    )

    val homeStateCompletedPhase = HomeUIState(
        showSpecializationBanner = true,
        lastUnfinishedHistoryPhase = completedPhase
    )

    val homeStateOnlySpecialization = HomeUIState(
        showSpecializationBanner = true,
        lastUnfinishedHistoryPhase = null
    )

    val homeStateOnlyHistory = HomeUIState(
        showSpecializationBanner = false,
        lastUnfinishedHistoryPhase = unfinishedPhase
    )
}
