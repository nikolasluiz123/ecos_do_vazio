package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

data class BattleMobTuple(
    val id: String,
    val strength: Long,
    val dexterity: Long,
    val intelligence: Long,
    val physicalResistance: Long,
    val magicResistance: Long,
    val vitality: Long,
    val agility: Long,
    val imageName: String,
    val mobCategory: MobCategory,
    val name: String,
    val description: String,
)
