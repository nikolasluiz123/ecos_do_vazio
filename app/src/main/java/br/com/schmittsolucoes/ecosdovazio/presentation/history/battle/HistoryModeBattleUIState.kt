package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel

data class HistoryModeBattleUIState(
    val phaseId: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val mobs: List<BattleMobUIModel> = emptyList(),
    val char: BattleCharUIModel? = null
)
