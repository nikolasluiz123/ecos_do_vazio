package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDot

data class ApplyDoTDamageResult(
    val mobsHealth: Map<String, Long>,
    val mobsDots: Map<String, List<ActiveDot>>
)
