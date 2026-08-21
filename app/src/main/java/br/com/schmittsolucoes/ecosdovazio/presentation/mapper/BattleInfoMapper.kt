package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.battle.ActiveDot
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.IdentifiedCharAttribute
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedSkillInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveDotUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

fun CharSkillUIModel.toDomainUsedSkillInfo(): UsedSkillInfo {
    return when (this) {
        is CharSkillUIModel.CommonDamage -> UsedSkillInfo.CommonDamage(
            refreshTime = refreshTime,
            damage = damage
        )

        is CharSkillUIModel.DamageOverTime -> UsedSkillInfo.DamageOverTime(
            refreshTime = refreshTime,
            damage = damage,
            duration = duration
        )

        is CharSkillUIModel.Buff -> UsedSkillInfo.Buff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration
        )

        is CharSkillUIModel.Debuff -> UsedSkillInfo.Debuff(
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
        actualHealth = actualHealth
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
        activeDots = activeDots.map { it.toDomain() }
    )
}

fun ActiveDotUIModel.toDomain(): ActiveDot {
    return ActiveDot(
        skillId = skillId,
        remainingTurns = remainingTurns,
        skillInfo = skillInfo
    )
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
