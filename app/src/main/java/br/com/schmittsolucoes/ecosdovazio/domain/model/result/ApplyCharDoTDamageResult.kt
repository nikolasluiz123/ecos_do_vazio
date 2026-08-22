package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDoT

data class ApplyCharDoTDamageResult(
    val charHealth: Long,
    val charDots: List<ActiveDoT.MobActiveDoT>
)
