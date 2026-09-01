package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import javax.inject.Inject

class BattleInfoMapper @Inject constructor(
    private val battleMapper: BattleMapper,
    private val skillMapper: SkillMapper
) {

    fun mapToUsedSkillInfo(skillUIModel: CharSkillUIModel): UsedCharSkillInfo {
        return when (skillUIModel) {
            is CharSkillUIModel.CommonDamage -> UsedCharSkillInfo.CommonDamage(
                refreshTime = skillUIModel.refreshTime,
                damage = skillUIModel.damage
            )

            is CharSkillUIModel.AreaDamage -> UsedCharSkillInfo.AreaDamage(
                refreshTime = skillUIModel.refreshTime,
                damage = skillUIModel.damage
            )

            is CharSkillUIModel.DamageOverTime -> UsedCharSkillInfo.DamageOverTime(
                refreshTime = skillUIModel.refreshTime,
                damage = skillUIModel.damage,
                duration = skillUIModel.duration
            )

            is CharSkillUIModel.VampiricDamage -> UsedCharSkillInfo.VampiricDamage(
                refreshTime = skillUIModel.refreshTime,
                damage = skillUIModel.damage,
                multiplier = skillUIModel.multiplier
            )

            is CharSkillUIModel.Buff -> UsedCharSkillInfo.Buff(
                skillCategory = skillUIModel.skillCategory,
                refreshTime = skillUIModel.refreshTime,
                multiplier = skillUIModel.multiplier,
                duration = skillUIModel.duration
            )

            is CharSkillUIModel.Debuff -> UsedCharSkillInfo.Debuff(
                skillCategory = skillUIModel.skillCategory,
                refreshTime = skillUIModel.refreshTime,
                multiplier = skillUIModel.multiplier,
                duration = skillUIModel.duration,
                damage = skillUIModel.damage
            )
        }
    }

    fun mapToUsedSkillInfo(skillUIModel: MobSkillUIModel): UsedMobSkillInfo {
        return when (skillUIModel) {
            is MobSkillUIModel.CommonDamage -> UsedMobSkillInfo.CommonDamage(
                refreshTime = skillUIModel.refreshTime,
                damage = skillUIModel.damage,
                skillId = skillUIModel.id
            )

            is MobSkillUIModel.DamageOverTime -> UsedMobSkillInfo.DamageOverTime(
                refreshTime = skillUIModel.refreshTime,
                damage = skillUIModel.damage,
                duration = skillUIModel.duration,
                skillId = skillUIModel.id
            )

            is MobSkillUIModel.VampiricDamage -> UsedMobSkillInfo.VampiricDamage(
                refreshTime = skillUIModel.refreshTime,
                damage = skillUIModel.damage,
                skillId = skillUIModel.id,
                multiplier = skillUIModel.multiplier
            )

            is MobSkillUIModel.Buff -> UsedMobSkillInfo.Buff(
                skillCategory = skillUIModel.skillCategory,
                refreshTime = skillUIModel.refreshTime,
                multiplier = skillUIModel.multiplier,
                duration = skillUIModel.duration,
                skillId = skillUIModel.id
            )

            is MobSkillUIModel.Debuff -> UsedMobSkillInfo.Debuff(
                skillCategory = skillUIModel.skillCategory,
                refreshTime = skillUIModel.refreshTime,
                multiplier = skillUIModel.multiplier,
                duration = skillUIModel.duration,
                skillId = skillUIModel.id
            )

            is MobSkillUIModel.Heal -> UsedMobSkillInfo.Heal(
                lifeRestore = skillUIModel.lifeRestore,
                refreshTime = skillUIModel.refreshTime,
                skillId = skillUIModel.id,
                skillCategory = skillUIModel.skillCategory
            )
        }
    }

    fun mapToDomainInfo(charUIModel: BattleCharUIModel): BattleCharInfo {
        return BattleCharInfo(
            classCategory = charUIModel.classCategory,
            offensiveMultiplier = charUIModel.offensiveMultiplier,
            defensiveMultiplier = charUIModel.defensiveMultiplier,
            attributes = listOf(
                IdentifiedCharAttribute(AttributeIdentifier.STRENGTH, charUIModel.strength),
                IdentifiedCharAttribute(AttributeIdentifier.DEXTERITY, charUIModel.dexterity),
                IdentifiedCharAttribute(AttributeIdentifier.INTELLIGENCE, charUIModel.intelligence),
                IdentifiedCharAttribute(AttributeIdentifier.PHYSICAL_RESISTANCE, charUIModel.physicalResistance),
                IdentifiedCharAttribute(AttributeIdentifier.MAGIC_RESISTANCE, charUIModel.magicResistance),
                IdentifiedCharAttribute(AttributeIdentifier.VITALITY, charUIModel.vitality),
                IdentifiedCharAttribute(AttributeIdentifier.AGILITY, charUIModel.agility)
            ),
            actualHealth = charUIModel.actualHealth,
            totalHealth = charUIModel.totalHealth,
            activeStatus = charUIModel.activeStatus.map { battleMapper.mapToDomain(it) },
            criticalFailCount = charUIModel.criticalFailCount
        )
    }

    fun mapToDomainInfo(mobUIModel: BattleMobUIModel): BattleMobInfo {
        return BattleMobInfo(
            mobCategory = mobUIModel.mobCategory,
            offensiveMultiplier = mobUIModel.offensiveMultiplier,
            defensiveMultiplier = mobUIModel.defensiveMultiplier,
            attributes = mobUIModel.attributes,
            level = mobUIModel.level,
            actualHealth = mobUIModel.actualHealth,
            totalHealth = mobUIModel.totalHealth,
            skills = mobUIModel.skills.map { skillMapper.mapToDomain(it) },
            activeStatus = mobUIModel.activeStatus.map { battleMapper.mapToDomain(it) },
            phaseMobId = mobUIModel.phaseMobId
        )
    }

}
