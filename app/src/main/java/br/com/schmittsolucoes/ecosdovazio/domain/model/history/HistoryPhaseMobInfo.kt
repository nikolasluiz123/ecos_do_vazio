package br.com.schmittsolucoes.ecosdovazio.domain.model.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.MobPhaseInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill

data class HistoryPhaseMobInfo(
    val mobPhaseInfo: MobPhaseInfo,
    val skills: List<MobSkill>,
)
