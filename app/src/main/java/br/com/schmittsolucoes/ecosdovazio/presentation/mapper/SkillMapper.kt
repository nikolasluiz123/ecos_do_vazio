package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel

fun CharSkill.toUIModel(
    image: Int,
    currentRefreshTime: Int,
    blocked: Boolean
): CharSkillUIModel {
    return when (this) {
        is CharSkill.CommonDamage -> CharSkillUIModel.CommonDamage(
            id = id,
            name = name,
            description = description,
            refreshTime = refreshTime,
            minLevel = minLevel,
            image = image,
            attributes = attributes,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            damage = damage
        )

        is CharSkill.DamageOverTime -> CharSkillUIModel.DamageOverTime(
            id = id,
            name = name,
            description = description,
            refreshTime = refreshTime,
            minLevel = minLevel,
            image = image,
            attributes = attributes,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            damage = damage,
            duration = duration
        )

        is CharSkill.Buff -> CharSkillUIModel.Buff(
            id = id,
            name = name,
            description = description,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            minLevel = minLevel,
            image = image,
            attributes = attributes,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            multiplier = multiplier,
            duration = duration
        )

        is CharSkill.Debuff -> CharSkillUIModel.Debuff(
            id = id,
            name = name,
            description = description,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            minLevel = minLevel,
            image = image,
            attributes = attributes,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            multiplier = multiplier,
            duration = duration
        )
    }
}

fun MobSkill.toUIModel(image: Int): MobSkillUIModel {
    return when (this) {
        is MobSkill.CommonDamage -> MobSkillUIModel.CommonDamage(
            id = id,
            name = name,
            description = description,
            image = image,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            damage = damage
        )

        is MobSkill.DamageOverTime -> MobSkillUIModel.DamageOverTime(
            id = id,
            name = name,
            description = description,
            image = image,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            damage = damage,
            duration = duration
        )

        is MobSkill.Buff -> MobSkillUIModel.Buff(
            id = id,
            name = name,
            description = description,
            image = image,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            multiplier = multiplier,
            duration = duration
        )

        is MobSkill.Debuff -> MobSkillUIModel.Debuff(
            id = id,
            name = name,
            description = description,
            image = image,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            multiplier = multiplier,
            duration = duration
        )
    }
}

fun MobSkillUIModel.toDomain(): MobSkill {
    return when (this) {
        is MobSkillUIModel.CommonDamage -> MobSkill.CommonDamage(
            id = id,
            name = name,
            description = description,
            imageName = "",
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            damage = damage
        )

        is MobSkillUIModel.DamageOverTime -> MobSkill.DamageOverTime(
            id = id,
            name = name,
            description = description,
            imageName = "",
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            damage = damage,
            duration = duration
        )

        is MobSkillUIModel.Buff -> MobSkill.Buff(
            id = id,
            name = name,
            description = description,
            imageName = "",
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            multiplier = multiplier,
            duration = duration
        )

        is MobSkillUIModel.Debuff -> MobSkill.Debuff(
            id = id,
            name = name,
            description = description,
            imageName = "",
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            minLevel = minLevel,
            multiplier = multiplier,
            duration = duration
        )
    }
}

fun MobSkill.toUsedInfo(): UsedMobSkillInfo {
    return when (this) {
        is MobSkill.CommonDamage -> UsedMobSkillInfo.CommonDamage(
            refreshTime = refreshTime,
            damage = damage
        )

        is MobSkill.DamageOverTime -> UsedMobSkillInfo.DamageOverTime(
            refreshTime = refreshTime,
            damage = damage,
            duration = duration
        )

        is MobSkill.Buff -> UsedMobSkillInfo.Buff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration
        )

        is MobSkill.Debuff -> UsedMobSkillInfo.Debuff(
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            multiplier = multiplier,
            duration = duration
        )
    }
}
