package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseData
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import kotlinx.coroutines.flow.Flow

class GetPhaseDataByIdUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val languageProvider: LanguageProvider,
) {
    operator fun invoke(phaseId: String): Flow<HistoryPhaseData?> {
        val languageTag = languageProvider.getDeviceTag()
        return historyPhaseRepository.getHistoryPhaseDataById(phaseId, languageTag)
    }
}
