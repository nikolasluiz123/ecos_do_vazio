package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel

data class HistoryPhaseMobInfoUIModel(
    val mobPhaseInfo: MobPhaseInfoUIModel,
    val skills: List<MobSkillUIModel>,
)
