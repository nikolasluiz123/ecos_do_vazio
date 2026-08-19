package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

data class BattleMobXPInfo(
    val category: MobCategory,
    val level: Long,
)