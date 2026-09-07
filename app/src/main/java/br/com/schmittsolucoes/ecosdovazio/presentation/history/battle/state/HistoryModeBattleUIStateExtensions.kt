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
