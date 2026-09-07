package br.com.schmittsolucoes.ecosdovazio.presentation.history.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryPhaseUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryUIState

object HistoryPreviewData {

    val phaseEspadaLascada = HistoryPhaseUIModel(
        id = "1",
        name = "Espada Lascada",
        imageResId = R.drawable.icone_fase_espada_lascada,
        isFinished = true,
        isActual = false,
    )

    val phaseEscudoRachado = HistoryPhaseUIModel(
        id = "2",
        name = "Escudo Rachado",
        imageResId = R.drawable.icone_fase_escudo_rachado,
        isFinished = false,
        isActual = true,
    )

    val phasePorreteGigante = HistoryPhaseUIModel(
        id = "3",
        name = "Porrete Gigante",
        imageResId = R.drawable.icone_fase_porrete_gigante,
        isFinished = false,
        isActual = false,
    )

    val phaseChamaRoxa = HistoryPhaseUIModel(
        id = "4",
        name = "Chama Roxa",
        imageResId = R.drawable.icone_fase_chama_roxa,
        isFinished = false,
        isActual = false,
    )

    val phaseCoracaoComAura = HistoryPhaseUIModel(
        id = "5",
        name = "Coração com Aura",
        imageResId = R.drawable.icone_fase_coracao_com_aura,
        isFinished = false,
        isActual = false,
    )

    val phaseList = listOf(
        phaseEspadaLascada,
        phaseEscudoRachado,
        phasePorreteGigante,
        phaseChamaRoxa,
        phaseCoracaoComAura,
    )

    val uiStateLoaded = HistoryUIState(
        phases = phaseList,
        actualPhaseIndex = 1,
        errorMessage = null,
        isLoading = false,
    )

    val uiStateWithError = HistoryUIState(
        phases = phaseList,
        actualPhaseIndex = 1,
        errorMessage = "Ocorreu um erro ao carregar as fases da história.",
        isLoading = false,
    )

    val uiStateLoading = HistoryUIState(
        phases = emptyList(),
        actualPhaseIndex = 0,
        errorMessage = null,
        isLoading = true,
    )
}
