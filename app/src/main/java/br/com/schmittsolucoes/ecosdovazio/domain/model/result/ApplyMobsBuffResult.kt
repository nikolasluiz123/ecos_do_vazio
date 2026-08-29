package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus

data class ApplyMobsBuffResult(
    val buffs: Map<String, List<MobActiveStatus.Buff>>
)
