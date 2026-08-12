package br.com.schmittsolucoes.ecosdovazio.presentation.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase

fun CharHistoryPhase.toUIModel(imageResId: Int): HistoryPhaseUIModel {
    return HistoryPhaseUIModel(
        id = phaseId,
        name = phaseName,
        imageResId = imageResId,
        isFinished = finishedAt != null,
        isActual = isActual
    )
}
