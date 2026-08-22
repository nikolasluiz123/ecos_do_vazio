package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDoT
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveDotUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
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
            damage = damage
        )

        is MobSkillUIModel.DamageOverTime -> UsedMobSkillInfo.DamageOverTime(
            refreshTime = refreshTime,
            damage = damage,
            duration = duration
        )

        is MobSkillUIModel.Buff -> UsedMobSkillInfo.Buff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration
        )

        is MobSkillUIModel.Debuff -> UsedMobSkillInfo.Debuff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration
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
        activeDots = activeDots.map { it.toDomain() }.filterIsInstance<ActiveDoT.MobActiveDoT>(),
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
        activeDots = activeDots.map { it.toDomain() }.filterIsInstance<ActiveDoT.CharActiveDoT>()
    )
}

fun ActiveDotUIModel.toDomain(): ActiveDoT {
    return when (this) {
        is ActiveDotUIModel.CharActiveDotUIModel -> ActiveDoT.CharActiveDoT(
            skillId = skillId,
            remainingTurns = remainingTurns,
            skillInfo = skillInfo
        )

        is ActiveDotUIModel.MobActiveDotUIModel -> ActiveDoT.MobActiveDoT(
            skillId = skillId,
            remainingTurns = remainingTurns,
            sourceId = sourceId,
            skillInfo = skillInfo
        )
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
        activeDots = activeDots
    )
}
