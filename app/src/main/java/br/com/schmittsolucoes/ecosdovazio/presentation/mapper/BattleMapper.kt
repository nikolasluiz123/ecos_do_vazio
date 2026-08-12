package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel

fun BattleChar.toUIModel(
    totalHealth: Long,
    actualHealth: Long,
    healthProgress: Float,
    @DrawableRes battleImage: Int
): BattleCharUIModel {
    return BattleCharUIModel(
        level = level,
        name = name,
        battleImage = battleImage,
        totalHealth = totalHealth,
        actualHealth = actualHealth,
        healthProgress = healthProgress,
        strength = strength,
        dexterity = dexterity,
        intelligence = intelligence,
        physicalResistance = physicalResistance,
        magicResistance = magicResistance,
        vitality = vitality,
        agility = agility
    )
}

fun BattleMob.toUIModel(
    totalHealth: Long,
    actualHealth: Long,
    healthProgress: Float,
    level: Long,
    @DrawableRes image: Int
): BattleMobUIModel {
    return BattleMobUIModel(
        id = id,
        name = name,
        description = description,
        image = image,
        mobCategory = mobCategory,
        totalHealth = totalHealth,
        actualHealth = actualHealth,
        healthProgress = healthProgress,
        level = level,
        attributes = attributes
    )
}
