package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus

data class ApplyMobsDebuffResult(
    val debuffs: Map<String, List<CharActiveStatus.Debuff>>
)
