package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus

data class ApplyCharDebuffResult(
    val debuffs: List<MobActiveStatus.Debuff>
)
