package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

fun BattleMobInfo.toXPInfo() = BattleMobXPInfo(
    category = mobCategory,
    level = level
)