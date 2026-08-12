package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar

data class BattleCharUIModel(
    val level: Long,
    val name: String,
    @DrawableRes val battleImage: Int,
    val totalHealth: Long,
    val actualHealth: Long,
    val healthProgress: Float,
    val strength: BattleChar.Attribute,
    val dexterity: BattleChar.Attribute,
    val intelligence: BattleChar.Attribute,
    val physicalResistance: BattleChar.Attribute,
    val magicResistance: BattleChar.Attribute,
    val vitality: BattleChar.Attribute,
    val agility: BattleChar.Attribute,
)
