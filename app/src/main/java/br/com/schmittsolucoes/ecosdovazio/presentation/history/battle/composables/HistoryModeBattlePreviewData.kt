package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

object HistoryModeBattlePreviewData {
    val mockMobWarrior = BattleMobUIModel(
        id = "1",
        name = "Goblin Guerreiro",
        description = "Um goblin armado com uma espada enferrujada.",
        image = R.drawable.goblin_guerreiro_16_9,
        mobCategory = MobCategory.WARRIOR,
        multiplier = 1.0,
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
        multiplier = 1.0,
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
        multiplier = 1.0,
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
        classCategory = ClassCategory.WARRIOR,
        multiplier = 1.0,
        totalHealth = 200,
        actualHealth = 150,
        healthProgress = 0.75f,
        strength = CharAttribute(15, 5, null),
        dexterity = CharAttribute(10, 2, null),
        intelligence = CharAttribute(5, 0, null),
        physicalResistance = CharAttribute(12, 3, null),
        magicResistance = CharAttribute(8, 2, null),
        vitality = CharAttribute(20, 5, null),
        agility = CharAttribute(12, 3, null)
    )

    val mockSkillDamage = CharSkillUIModel.CommonDamage(
        id = "skill_1",
        name = "Golpe Pesado",
        description = "Um ataque que causa dano físico massivo.",
        refreshTime = 3,
        image = R.drawable.skill_golpe_pesado,
        currentRefreshTime = 0,
        blocked = false,
        damage = 50
    )

    val mockSkillBuff = CharSkillUIModel.Buff(
        id = "skill_2",
        name = "Fúria de Batalha",
        description = "Aumenta o dano causado por alguns turnos.",
        skillCategory = SkillCategory.OFFENSIVE_BUFF,
        refreshTime = 5,
        image = R.drawable.skill_furia_de_batalha,
        currentRefreshTime = 0,
        blocked = false,
        multiplier = 1.5,
        duration = 3
    )

    val uiState = HistoryModeBattleUIState(
        mobs = mockMobsList,
        char = mockChar,
        damageSkills = listOf(mockSkillDamage),
        buffSkills = listOf(mockSkillBuff)
    )
}
