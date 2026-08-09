package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase

interface HistoryPhaseRepository {
    suspend fun save(historyPhases: List<HistoryPhase>)
}
