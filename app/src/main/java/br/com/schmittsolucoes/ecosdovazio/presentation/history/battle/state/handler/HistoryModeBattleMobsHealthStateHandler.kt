package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import javax.inject.Inject

class HistoryModeBattleMobsHealthStateHandler @Inject constructor() {

    fun updateMobHealth(
        currentState: HistoryModeBattleInternalState,
        mobs: List<BattleMobUIModel>,
        mobToUpdate: BattleMobUIModel,
        newEnemyHealth: Long,
    ): HistoryModeBattleInternalState {
        var currentHealths = currentState.mobsHealth

        if (currentHealths.isEmpty()) {
            currentHealths = mobs.associate { it.phaseMobId to it.actualHealth }
        }

        val updatedHealths = currentHealths.toMutableMap().apply {
            put(mobToUpdate.phaseMobId, newEnemyHealth)
        }

        val currentSelectedId = currentState.selectedMobId ?: mobs.firstOrNull()?.phaseMobId
        var newSelectedMobId = currentState.selectedMobId

        if (newEnemyHealth <= 0 && mobToUpdate.phaseMobId == currentSelectedId) {
            val nextMob = mobs.firstOrNull { mob ->
                val health = updatedHealths[mob.phaseMobId] ?: 0L
                health > 0
            }

            if (nextMob != null) {
                newSelectedMobId = nextMob.phaseMobId
            }
        }

        return currentState.copy(
            mobsHealth = updatedHealths,
            selectedMobId = newSelectedMobId,
        )
    }
}
