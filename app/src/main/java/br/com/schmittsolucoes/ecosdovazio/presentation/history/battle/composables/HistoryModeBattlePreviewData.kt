package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.composables

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedSkillInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveDotUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

object HistoryModeBattlePreviewData {
    val mockActiveDot = ActiveDotUIModel(
        skillId = "dot_1",
        skillName = "Veneno",
        remainingTurns = 3,
        skillInfo = UsedSkillInfo.DamageOverTime(
            refreshTime = 3,
            damage = 10,
            duration = 3
        ),
        skillImage = R.drawable.skill_golpe_pesado
    )

    val mockMobWarrior = BattleMobUIModel(
        mobId = "1",
        phaseMobId = "1",
        name = "Goblin Guerreiro",
        description = "Um goblin armado com uma espada enferrujada.",
        image = R.drawable.goblin_guerreiro_16_9,
        mobCategory = MobCategory.WARRIOR,
        offensiveMultiplier = 1.0,
        defensiveMultiplier = 0.0,
        totalHealth = 100,
        actualHealth = 75,
        healthProgress = 0.75f,
        level = 5,
        attributes = Mob.Attributes(strength = 10, vitality = 8),
        activeDots = listOf(mockActiveDot, mockActiveDot.copy(skillId = "dot_2", skillName = "Sangramento"))
    )

    val mockMobMage = BattleMobUIModel(
        mobId = "2",
        phaseMobId = "2",
        name = "Goblin Xamã",
        description = "Um goblin que domina magias básicas.",
        image = R.drawable.goblin_xama_16_9,
        mobCategory = MobCategory.MAGE,
        offensiveMultiplier = 1.0,
        defensiveMultiplier = 0.0,
        totalHealth = 80,
        actualHealth = 80,
        healthProgress = 1f,
        level = 4,
        attributes = Mob.Attributes(intelligence = 12, vitality = 6)
    )

    val mockMobOrc = BattleMobUIModel(
        mobId = "3",
        phaseMobId = "3",
        name = "Orc das Cavernas",
        description = "Um orc brutal das profundezas.",
        image = R.drawable.orc_das_cavernas_16_9,
        mobCategory = MobCategory.ORC_WARRIOR,
        offensiveMultiplier = 1.0,
        defensiveMultiplier = 0.0,
        totalHealth = 150,
        actualHealth = 150,
        healthProgress = 1f,
        level = 7,
        attributes = Mob.Attributes(strength = 18, vitality = 15)
    )

    val mockMobsList = listOf(mockMobWarrior, mockMobMage, mockMobOrc)

    val mockSkillAttributes = CharSkill.Attributes(
        requiredStrength = 0,
        requiredDexterity = 0,
        requiredIntelligence = 0,
        requiredPhysicalResistance = 0,
        requiredMagicResistance = 0,
        requiredVitality = 0,
        requiredAgility = 0
    )

    val mockSkillDamage = CharSkillUIModel.CommonDamage(
        id = "skill_1",
        name = "Golpe Pesado",
        description = "Um ataque que causa dano físico massivo.",
        refreshTime = 3,
        minLevel = 1,
        image = R.drawable.skill_golpe_pesado,
        attributes = mockSkillAttributes,
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
        minLevel = 5,
        image = R.drawable.skill_furia_de_batalha,
        attributes = mockSkillAttributes,
        currentRefreshTime = 0,
        blocked = false,
        multiplier = 1.5,
        duration = 3
    )

    val mockChar = BattleCharUIModel(
        level = 10,
        name = "Herói",
        battleImage = R.drawable.classe_guerreiro_16_9,
        classCategory = ClassCategory.WARRIOR,
        offensiveMultiplier = 1.0,
        defensiveMultiplier = 0.0,
        totalHealth = 200,
        actualHealth = 150,
        healthProgress = 0.75f,
        strength = CharAttribute(15, 5, null),
        dexterity = CharAttribute(10, 2, null),
        intelligence = CharAttribute(5, 0, null),
        physicalResistance = CharAttribute(12, 3, null),
        magicResistance = CharAttribute(8, 2, null),
        vitality = CharAttribute(20, 5, null),
        agility = CharAttribute(12, 3, null),
        damageSkills = listOf(mockSkillDamage),
        buffSkills = listOf(mockSkillBuff)
    )

    val uiState = HistoryModeBattleUIState(
        mobs = mockMobsList,
        char = mockChar,
    )
}
