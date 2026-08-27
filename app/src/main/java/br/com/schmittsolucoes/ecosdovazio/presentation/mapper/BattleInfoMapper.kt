package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.CharActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.MobActiveStatus
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel

fun CharSkillUIModel.toDomainUsedSkillInfo(): UsedCharSkillInfo {
    return when (this) {
        is CharSkillUIModel.CommonDamage -> UsedCharSkillInfo.CommonDamage(
            refreshTime = refreshTime,
            damage = damage
        )

        is CharSkillUIModel.DamageOverTime -> UsedCharSkillInfo.DamageOverTime(
            refreshTime = refreshTime,
            damage = damage,
            duration = duration
        )

        is CharSkillUIModel.Buff -> UsedCharSkillInfo.Buff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration
        )

        is CharSkillUIModel.Debuff -> UsedCharSkillInfo.Debuff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration
        )
    }
}

fun MobSkillUIModel.toDomainUsedSkillInfo(): UsedMobSkillInfo {
    return when (this) {
        is MobSkillUIModel.CommonDamage -> UsedMobSkillInfo.CommonDamage(
            refreshTime = refreshTime,
            damage = damage,
            skillId = id
        )

        is MobSkillUIModel.DamageOverTime -> UsedMobSkillInfo.DamageOverTime(
            refreshTime = refreshTime,
            damage = damage,
            duration = duration,
            skillId = id
        )

        is MobSkillUIModel.Buff -> UsedMobSkillInfo.Buff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration,
            skillId = id
        )

        is MobSkillUIModel.Debuff -> UsedMobSkillInfo.Debuff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration,
            skillId = id
        )
    }
}

fun BattleCharUIModel.toDomainInfo(): BattleCharInfo {
    return BattleCharInfo(
        classCategory = classCategory,
        offensiveMultiplier = offensiveMultiplier,
        defensiveMultiplier = defensiveMultiplier,
        attributes = listOf(
            IdentifiedCharAttribute(AttributeIdentifier.STRENGTH, strength),
            IdentifiedCharAttribute(AttributeIdentifier.DEXTERITY, dexterity),
            IdentifiedCharAttribute(AttributeIdentifier.INTELLIGENCE, intelligence),
            IdentifiedCharAttribute(AttributeIdentifier.PHYSICAL_RESISTANCE, physicalResistance),
            IdentifiedCharAttribute(AttributeIdentifier.MAGIC_RESISTANCE, magicResistance),
            IdentifiedCharAttribute(AttributeIdentifier.VITALITY, vitality),
            IdentifiedCharAttribute(AttributeIdentifier.AGILITY, agility)
        ),
        actualHealth = actualHealth,
        activeStatus = activeStatus.map { it.toDomain() },
        criticalFailCount = criticalFailCount
    )
}

fun BattleMobUIModel.toDomainInfo(): BattleMobInfo {
    return BattleMobInfo(
        mobCategory = mobCategory,
        offensiveMultiplier = offensiveMultiplier,
        defensiveMultiplier = defensiveMultiplier,
        attributes = attributes,
        level = level,
        actualHealth = actualHealth,
        skills = skills.map { it.toDomain() },
        activeStatus = activeStatus.map { it.toDomain() }
    )
}

fun CharActiveStatusUIModel.toDomain(): CharActiveStatus {
    return when (this) {
        is CharActiveStatusUIModel.DoTUIModel -> {
            CharActiveStatus.DoT(
                skillId = skillId,
                remainingTurns = remainingTurns,
                skillInfo = skillInfo
            )
        }

        is CharActiveStatusUIModel.DebuffUIModel -> {
            CharActiveStatus.Debuff(
                skillId = skillId,
                remainingTurns = remainingTurns,
                skillInfo = skillInfo,
                skillCategory = skillCategory
            )
        }
    }
}

fun MobActiveStatusUIModel.toDomain(): MobActiveStatus {
    return when (this) {
        is MobActiveStatusUIModel.DoTUIModel -> {
            MobActiveStatus.DoT(
                skillId = skillId,
                remainingTurns = remainingTurns,
                sourceId = sourceId,
                skillInfo = skillInfo
            )
        }

        is MobActiveStatusUIModel.DebuffUIModel -> {
            MobActiveStatus.Debuff(
                skillId = skillId,
                remainingTurns = remainingTurns,
                sourceId = sourceId,
                skillInfo = skillInfo,
                skillCategory = skillCategory
            )
        }
    }
}

fun BattleMob.toInfo(): BattleMobInfo {
    return BattleMobInfo(
        mobCategory = mobCategory,
        offensiveMultiplier = offensiveMultiplier,
        defensiveMultiplier = defensiveMultiplier,
        attributes = attributes,
        level = level,
        actualHealth = actualHealth,
        skills = skills,
        activeStatus = activeStatus
    )
}
