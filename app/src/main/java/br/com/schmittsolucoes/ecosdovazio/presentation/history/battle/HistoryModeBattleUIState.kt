package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

data class HistoryModeBattleUIState(
    val phaseId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mobs: List<BattleMobUIModel> = emptyList(),
    val selectedMob: BattleMobUIModel? = null,
    val char: BattleCharUIModel? = null,
    val selectedSkill: CharSkillUIModel? = null,
    val actualRound: Long = 1,
)
