package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import androidx.annotation.DrawableRes
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel

fun BattleChar.toUIModel(
    totalHealth: Long,
    actualHealth: Long,
    healthProgress: Float,
    @DrawableRes battleImage: Int,
    offensiveMultiplier: Double,
    defensiveMultiplier: Double,
    damageSkills: List<CharSkillUIModel> = emptyList(),
    buffSkills: List<CharSkillUIModel> = emptyList(),
    debuffSkills: List<CharSkillUIModel> = emptyList(),
    activeStatus: List<MobActiveStatusUIModel> = emptyList()
): BattleCharUIModel {
    return BattleCharUIModel(
        level = level,
        name = name,
        battleImage = battleImage,
        classCategory = classCategory,
        offensiveMultiplier = offensiveMultiplier,
        defensiveMultiplier = defensiveMultiplier,
        totalHealth = totalHealth,
        actualHealth = actualHealth,
        healthProgress = healthProgress,
        strength = strength,
        dexterity = dexterity,
        intelligence = intelligence,
        physicalResistance = physicalResistance,
        magicResistance = magicResistance,
        vitality = vitality,
        agility = agility,
        damageSkills = damageSkills,
        buffSkills = buffSkills,
        debuffSkills = debuffSkills,
        activeStatus = activeStatus
    )
}

fun BattleMob.toUIModel(
    totalHealth: Long,
    healthProgress: Float,
    @DrawableRes image: Int,
    skills: List<MobSkillUIModel> = emptyList(),
    activeStatus: List<CharActiveStatusUIModel> = emptyList()
): BattleMobUIModel {
    return BattleMobUIModel(
        mobId = mobId,
        phaseMobId = phaseMobId,
        name = name,
        description = description,
        image = image,
        mobCategory = mobCategory,
        offensiveMultiplier = offensiveMultiplier,
        defensiveMultiplier = defensiveMultiplier,
        totalHealth = totalHealth,
        actualHealth = actualHealth,
        healthProgress = healthProgress,
        level = level,
        attributes = attributes,
        skills = skills,
        activeStatus = activeStatus
    )
}

fun CharActiveStatus.toUIModel(
    skillName: String,
    skillDescription: String,
    skillImage: Int
): CharActiveStatusUIModel {
    return when (this) {
        is CharActiveStatus.DoT -> {
            CharActiveStatusUIModel.DoTUIModel(
                skillId = skillId,
                skillName = skillName,
                skillDescription = skillDescription,
                remainingTurns = remainingTurns,
                skillImage = skillImage,
                skillInfo = skillInfo
            )
        }

        is CharActiveStatus.Debuff -> {
            CharActiveStatusUIModel.DebuffUIModel(
                skillId = skillId,
                skillName = skillName,
                skillDescription = skillDescription,
                remainingTurns = remainingTurns,
                skillImage = skillImage,
                skillCategory = skillCategory,
                skillInfo = skillInfo
            )
        }
    }
}

fun MobActiveStatus.toUIModel(
    skillName: String,
    skillDescription: String,
    skillImage: Int
): MobActiveStatusUIModel {
    return when (this) {
        is MobActiveStatus.DoT -> {
            MobActiveStatusUIModel.DoTUIModel(
                skillId = skillId,
                skillName = skillName,
                skillDescription = skillDescription,
                remainingTurns = remainingTurns,
                skillImage = skillImage,
                sourceId = sourceId,
                skillInfo = skillInfo
            )
        }

        is MobActiveStatus.Debuff -> {
            MobActiveStatusUIModel.DebuffUIModel(
                skillId = skillId,
                skillName = skillName,
                skillDescription = skillDescription,
                remainingTurns = remainingTurns,
                skillImage = skillImage,
                skillCategory = skillCategory,
                sourceId = sourceId,
                skillInfo = skillInfo
            )
        }
    }
}

fun BattleMobUIModel.toDomain(): BattleMob {
    return BattleMob(
        mobId = mobId,
        phaseMobId = phaseMobId,
        name = name,
        description = description,
        imageName = "",
        mobCategory = mobCategory,
        level = level,
        offensiveMultiplier = offensiveMultiplier,
        defensiveMultiplier = defensiveMultiplier,
        actualHealth = actualHealth,
        attributes = attributes,
        skills = skills.map { it.toDomain() },
        activeStatus = activeStatus.map { it.toDomain() }
    )
}
