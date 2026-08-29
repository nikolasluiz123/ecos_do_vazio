package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus

data class ApplyCharBuffResult(
    val buffs: List<CharActiveStatus.Buff>
)
