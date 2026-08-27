package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus

data class ApplyMobsDoTResult(
    val mobsHealth: Map<String, Long>,
    val dots: Map<String, List<CharActiveStatus.DoT>>
)
