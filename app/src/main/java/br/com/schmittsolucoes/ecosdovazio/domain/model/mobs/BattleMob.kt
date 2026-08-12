package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

data class BattleMob(
    val id: String,
    val name: String,
    val description: String,
    val imageName: String,
    val mobCategory: MobCategory,
    val attributes: Mob.Attributes
)
