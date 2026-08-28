package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import javax.inject.Inject

class BattleMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val skillMapper: SkillMapper
) {

    fun mapToUIModel(
        char: BattleChar,
        totalHealth: Long,
        actualHealth: Long,
        healthProgress: Float,
        offensiveMultiplier: Double,
        defensiveMultiplier: Double,
        damageSkills: List<CharSkillUIModel> = emptyList(),
        buffSkills: List<CharSkillUIModel> = emptyList(),
        debuffSkills: List<CharSkillUIModel> = emptyList(),
        activeStatus: List<MobActiveStatusUIModel> = emptyList()
    ): BattleCharUIModel {
        val battleImage = resourcesProvider.getBattleClassImage(char.battleImageName)
            ?: resourcesProvider.getBattleSpecializationImage(char.battleImageName)
            ?: 0

        return BattleCharUIModel(
            level = char.level,
            name = char.name,
            battleImage = battleImage,
            classCategory = char.classCategory,
            offensiveMultiplier = offensiveMultiplier,
            defensiveMultiplier = defensiveMultiplier,
            totalHealth = totalHealth,
            actualHealth = actualHealth,
            healthProgress = healthProgress,
            strength = char.strength,
            dexterity = char.dexterity,
            intelligence = char.intelligence,
            physicalResistance = char.physicalResistance,
            magicResistance = char.magicResistance,
            vitality = char.vitality,
            agility = char.agility,
            damageSkills = damageSkills,
            buffSkills = buffSkills,
            debuffSkills = debuffSkills,
            activeStatus = activeStatus
        )
    }

    fun mapToUIModel(
        battleMob: BattleMob,
        totalHealth: Long,
        healthProgress: Float,
        skills: List<MobSkillUIModel> = emptyList(),
        activeStatus: List<CharActiveStatusUIModel> = emptyList()
    ): BattleMobUIModel {
        return BattleMobUIModel(
            mobId = battleMob.mobId,
            phaseMobId = battleMob.phaseMobId,
            name = battleMob.name,
            description = battleMob.description,
            image = resourcesProvider.getBattleMobImage(battleMob.imageName) ?: 0,
            mobCategory = battleMob.mobCategory,
            offensiveMultiplier = battleMob.offensiveMultiplier,
            defensiveMultiplier = battleMob.defensiveMultiplier,
            totalHealth = totalHealth,
            actualHealth = battleMob.actualHealth,
            healthProgress = healthProgress,
            level = battleMob.level,
            attributes = battleMob.attributes,
            skills = skills,
            activeStatus = activeStatus
        )
    }

    fun mapToUIModel(
        charActiveStatus: CharActiveStatus,
        skillName: String,
        skillDescription: String,
        skillImage: Int
    ): CharActiveStatusUIModel {
        return when (charActiveStatus) {
            is CharActiveStatus.DoT -> {
                CharActiveStatusUIModel.DoTUIModel(
                    skillId = charActiveStatus.skillId,
                    skillName = skillName,
                    skillDescription = skillDescription,
                    remainingTurns = charActiveStatus.remainingTurns,
                    skillImage = skillImage,
                    skillInfo = charActiveStatus.skillInfo
                )
            }

            is CharActiveStatus.Debuff -> {
                CharActiveStatusUIModel.DebuffUIModel(
                    skillId = charActiveStatus.skillId,
                    skillName = skillName,
                    skillDescription = skillDescription,
                    remainingTurns = charActiveStatus.remainingTurns,
                    skillImage = skillImage,
                    skillCategory = charActiveStatus.skillCategory,
                    skillInfo = charActiveStatus.skillInfo
                )
            }
        }
    }

    fun mapToUIModel(
        mobActiveStatus: MobActiveStatus,
        skillName: String,
        skillDescription: String,
        skillImage: Int
    ): MobActiveStatusUIModel {
        return when (mobActiveStatus) {
            is MobActiveStatus.DoT -> {
                MobActiveStatusUIModel.DoTUIModel(
                    skillId = mobActiveStatus.skillId,
                    skillName = skillName,
                    skillDescription = skillDescription,
                    remainingTurns = mobActiveStatus.remainingTurns,
                    skillImage = skillImage,
                    sourceId = mobActiveStatus.sourceId,
                    skillInfo = mobActiveStatus.skillInfo
                )
            }

            is MobActiveStatus.Debuff -> {
                MobActiveStatusUIModel.DebuffUIModel(
                    skillId = mobActiveStatus.skillId,
                    skillName = skillName,
                    skillDescription = skillDescription,
                    remainingTurns = mobActiveStatus.remainingTurns,
                    skillImage = skillImage,
                    skillCategory = mobActiveStatus.skillCategory,
                    sourceId = mobActiveStatus.sourceId,
                    skillInfo = mobActiveStatus.skillInfo
                )
            }
        }
    }

    fun mapToDomain(mobUIModel: BattleMobUIModel): BattleMob {
        return BattleMob(
            mobId = mobUIModel.mobId,
            phaseMobId = mobUIModel.phaseMobId,
            name = mobUIModel.name,
            description = mobUIModel.description,
            imageName = "",
            mobCategory = mobUIModel.mobCategory,
            level = mobUIModel.level,
            offensiveMultiplier = mobUIModel.offensiveMultiplier,
            defensiveMultiplier = mobUIModel.defensiveMultiplier,
            actualHealth = mobUIModel.actualHealth,
            attributes = mobUIModel.attributes,
            skills = mobUIModel.skills.map { skillMapper.mapToDomain(it) },
            activeStatus = mobUIModel.activeStatus.map { mapToDomain(it) }
        )
    }

    fun mapToDomain(charActiveStatusUIModel: CharActiveStatusUIModel): CharActiveStatus {
        return when (charActiveStatusUIModel) {
            is CharActiveStatusUIModel.DoTUIModel -> {
                CharActiveStatus.DoT(
                    skillId = charActiveStatusUIModel.skillId,
                    remainingTurns = charActiveStatusUIModel.remainingTurns,
                    skillInfo = charActiveStatusUIModel.skillInfo
                )
            }

            is CharActiveStatusUIModel.DebuffUIModel -> {
                CharActiveStatus.Debuff(
                    skillId = charActiveStatusUIModel.skillId,
                    remainingTurns = charActiveStatusUIModel.remainingTurns,
                    skillInfo = charActiveStatusUIModel.skillInfo,
                    skillCategory = charActiveStatusUIModel.skillCategory
                )
            }
        }
    }

    fun mapToDomain(mobActiveStatusUIModel: MobActiveStatusUIModel): MobActiveStatus {
        return when (mobActiveStatusUIModel) {
            is MobActiveStatusUIModel.DoTUIModel -> {
                MobActiveStatus.DoT(
                    skillId = mobActiveStatusUIModel.skillId,
                    remainingTurns = mobActiveStatusUIModel.remainingTurns,
                    sourceId = mobActiveStatusUIModel.sourceId,
                    skillInfo = mobActiveStatusUIModel.skillInfo
                )
            }

            is MobActiveStatusUIModel.DebuffUIModel -> {
                MobActiveStatus.Debuff(
                    skillId = mobActiveStatusUIModel.skillId,
                    remainingTurns = mobActiveStatusUIModel.remainingTurns,
                    sourceId = mobActiveStatusUIModel.sourceId,
                    skillInfo = mobActiveStatusUIModel.skillInfo,
                    skillCategory = mobActiveStatusUIModel.skillCategory
                )
            }
        }
    }
}
