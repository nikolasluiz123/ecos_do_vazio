package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel

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
            image = image,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            damage = damage
        )

        is CharSkill.DamageOverTime -> CharSkillUIModel.DamageOverTime(
            id = id,
            name = name,
            description = description,
            refreshTime = refreshTime,
            image = image,
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
            image = image,
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
            image = image,
            currentRefreshTime = currentRefreshTime,
            blocked = blocked,
            multiplier = multiplier,
            duration = duration
        )
    }
}
