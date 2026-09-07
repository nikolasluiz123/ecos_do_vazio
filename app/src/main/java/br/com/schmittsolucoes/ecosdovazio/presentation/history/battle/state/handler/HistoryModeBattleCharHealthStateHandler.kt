package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import javax.inject.Inject

class HistoryModeBattleCharHealthStateHandler @Inject constructor() {

    fun updateCharHealth(
        currentState: HistoryModeBattleInternalState,
        newHealth: Long,
    ): HistoryModeBattleInternalState {
        return currentState.copy(charHealth = newHealth)
    }
}
