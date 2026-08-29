package br.com.schmittsolucoes.ecosdovazio.domain.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobXPInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo

fun MobSkill.toUsedInfo(): UsedMobSkillInfo {
    return when (this) {
        is MobSkill.CommonDamage -> UsedMobSkillInfo.CommonDamage(
            refreshTime = refreshTime,
            damage = damage,
            skillId = id
        )

        is MobSkill.DamageOverTime -> UsedMobSkillInfo.DamageOverTime(
            refreshTime = refreshTime,
            damage = damage,
            duration = duration,
            skillId = id
        )

        is MobSkill.Buff -> UsedMobSkillInfo.Buff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration,
            skillId = id
        )

        is MobSkill.Debuff -> UsedMobSkillInfo.Debuff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration,
            skillId = id
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
        activeStatus = activeStatus,
        phaseMobId = phaseMobId
    )
}

fun BattleMobInfo.toXPInfo() = BattleMobXPInfo(
    category = mobCategory,
    level = level
)
