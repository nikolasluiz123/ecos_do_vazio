package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

data class HistoryModeBattleInternalState(
    val errorMessage: String? = null,
    val selectedMobId: String? = null,
    val selectedSkill: CharSkillUIModel? = null,
    val charHealth: Long? = null,
    val mobsHealth: Map<String, Long> = emptyMap(),
    val mobsActiveStatus: Map<String, List<ActiveStatusUIModel>> = emptyMap(),
    val charActiveStatus: List<ActiveStatusUIModel> = emptyList(),
    val skillsRefreshTime: Map<String, Int> = emptyMap(),
    val actualRound: Long = 1,
    val shouldPop: Boolean = false,
    val selectedDot: ActiveStatusUIModel? = null,
)