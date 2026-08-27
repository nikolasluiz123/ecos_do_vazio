package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus

data class ApplyCharDoTResult(
    val charHealth: Long,
    val dots: List<MobActiveStatus.DoT>
)
