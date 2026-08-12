package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository

class GetMobLevelUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository
) {
    suspend operator fun invoke(phaseId: String): Long {
        val phase = historyPhaseRepository.getById(phaseId)!!
        return phase.phaseNumber.toLong()
    }
}