package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.history.HistoryPhaseUIModel
import javax.inject.Inject

class HistoryMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    fun mapToUIModel(phase: CharHistoryPhase): HistoryPhaseUIModel {
        val imageResId = resourcesProvider.getPhaseImage(phase.imageName) ?: 0
        return HistoryPhaseUIModel(
            id = phase.phaseId,
            name = phase.phaseName,
            imageResId = imageResId,
            isFinished = phase.finishedAt != null,
            isActual = phase.isActual
        )
    }
}
