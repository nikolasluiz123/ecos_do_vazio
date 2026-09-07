package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel

fun HistoryModeBattleUIState.getMobById(id: String): BattleMobUIModel? {
    return mobs.firstOrNull { it.phaseMobId == id }
}

fun HistoryModeBattleUIState.getSelectedMobOrFirst(selectedMobId: String?): BattleMobUIModel? {
    return mobs.firstOrNull { it.phaseMobId == selectedMobId } ?: mobs.firstOrNull()
}

fun BattleMobUIModel.getMobSkill(skillId: String): MobSkillUIModel? {
    return skills.firstOrNull { it.id == skillId }
}

fun HistoryModeBattleUIState.charIsDead(currentState: HistoryModeBattleInternalState): Boolean {
    val notLoaded = currentState.charHealth == null && char?.actualHealth == null
    if (notLoaded) return false

    return (currentState.charHealth ?: char?.actualHealth ?: 0) <= 0
}

fun HistoryModeBattleUIState.allMobsIsDead(currentState: HistoryModeBattleInternalState): Boolean {
    val notLoaded = currentState.mobsHealth.isEmpty() && mobs.all { it.actualHealth <= 0 }
    if (notLoaded) return false

    val mobsHealth = currentState.mobsHealth.ifEmpty {
        mobs.associate { it.phaseMobId to it.actualHealth }
    }

    return mobsHealth.all { it.value <= 0 }
}
