package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDoT

data class ApplyMobsDoTDamageResult(
    val mobsHealth: Map<String, Long>,
    val mobsDots: Map<String, List<ActiveDoT.CharActiveDoT>>
)
