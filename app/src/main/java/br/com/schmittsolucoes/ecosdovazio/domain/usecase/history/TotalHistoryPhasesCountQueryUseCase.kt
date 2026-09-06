package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import kotlinx.coroutines.flow.Flow

class TotalHistoryPhasesCountQueryUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
) {
    operator fun invoke(): Flow<Int> {
        return historyPhaseRepository.getTotalHistoryPhasesCount()
    }
}
