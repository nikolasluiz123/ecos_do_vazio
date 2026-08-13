package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel

object HistoryModeBattlePreviewData {
    val mockMobWarrior = BattleMobUIModel(
        id = "1",
        name = "Goblin Guerreiro",
        description = "Um goblin armado com uma espada enferrujada.",
        image = R.drawable.goblin_guerreiro_16_9,
        mobCategory = MobCategory.WARRIOR,
        totalHealth = 100,
        actualHealth = 75,
        healthProgress = 0.75f,
        level = 5,
        attributes = Mob.Attributes(strength = 10, vitality = 8)
    )

    val mockMobMage = BattleMobUIModel(
        id = "2",
        name = "Goblin Xamã",
        description = "Um goblin que domina magias básicas.",
        image = R.drawable.goblin_xama_16_9,
        mobCategory = MobCategory.MAGE,
        totalHealth = 80,
        actualHealth = 80,
        healthProgress = 1f,
        level = 4,
        attributes = Mob.Attributes(intelligence = 12, vitality = 6)
    )

    val mockMobOrc = BattleMobUIModel(
        id = "3",
        name = "Orc das Cavernas",
        description = "Um orc brutal das profundezas.",
        image = R.drawable.orc_das_cavernas_16_9,
        mobCategory = MobCategory.ORC_WARRIOR,
        totalHealth = 150,
        actualHealth = 150,
        healthProgress = 1f,
        level = 7,
        attributes = Mob.Attributes(strength = 18, vitality = 15)
    )

    val mockMobsList = listOf(mockMobWarrior, mockMobMage, mockMobOrc)

    val mockChar = BattleCharUIModel(
        level = 10,
        name = "Herói",
        battleImage = R.drawable.classe_guerreiro_16_9,
        totalHealth = 200,
        actualHealth = 150,
        healthProgress = 0.75f,
        strength = BattleChar.Attribute(15, 5, null),
        dexterity = BattleChar.Attribute(10, 2, null),
        intelligence = BattleChar.Attribute(5, 0, null),
        physicalResistance = BattleChar.Attribute(12, 3, null),
        magicResistance = BattleChar.Attribute(8, 2, null),
        vitality = BattleChar.Attribute(20, 5, null),
        agility = BattleChar.Attribute(12, 3, null)
    )

    val uiState = HistoryModeBattleUIState(
        mobs = mockMobsList,
        char = mockChar
    )
}
