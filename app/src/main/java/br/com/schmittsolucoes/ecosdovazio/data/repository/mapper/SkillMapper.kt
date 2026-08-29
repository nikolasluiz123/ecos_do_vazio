package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.SkillEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSkillTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.MobSkillTuple
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkillDetails
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill

fun Skill.toEntity() = SkillEntity(
    id = id,
    nameTranslationId = nameTranslationId.name,
    descriptionTranslationId = descriptionTranslationId.name,
    skillCategory = skillCategory,
    classId = classId,
    specializationId = specializationId,
    mobId = mobId,
    damage = damage,
    multiplier = multiplier,
    duration = duration,
    refreshTime = refreshTime,
    minLevel = minLevel,
    imageName = imageName,
    requiredStrength = attributes.requiredStrength,
    requiredDexterity = attributes.requiredDexterity,
    requiredIntelligence = attributes.requiredIntelligence,
    requiredPhysicalResistance = attributes.requiredPhysicalResistance,
    requiredMagicResistance = attributes.requiredMagicResistance,
    requiredVitality = attributes.requiredVitality,
    requiredAgility = attributes.requiredAgility
)

fun SkillEntity.toDomain() = Skill(
    id = id,
    nameTranslationId = TranslationIdentifier.valueOf(nameTranslationId),
    descriptionTranslationId = TranslationIdentifier.valueOf(descriptionTranslationId),
    skillCategory = skillCategory,
    classId = classId,
    specializationId = specializationId,
    mobId = mobId,
    damage = damage,
    multiplier = multiplier,
    duration = duration,
    refreshTime = refreshTime,
    minLevel = minLevel,
    imageName = imageName,
    attributes = Skill.Attributes(
        requiredStrength = requiredStrength,
        requiredDexterity = requiredDexterity,
        requiredIntelligence = requiredIntelligence,
        requiredPhysicalResistance = requiredPhysicalResistance,
        requiredMagicResistance = requiredMagicResistance,
        requiredVitality = requiredVitality,
        requiredAgility = requiredAgility
    )
)

fun CharSkillTuple.toDomain(): CharSkill {
    val attributes = CharSkill.Attributes(
        requiredStrength = requiredStrength,
        requiredDexterity = requiredDexterity,
        requiredIntelligence = requiredIntelligence,
        requiredPhysicalResistance = requiredPhysicalResistance,
        requiredMagicResistance = requiredMagicResistance,
        requiredVitality = requiredVitality,
        requiredAgility = requiredAgility
    )

    return when (skillCategory) {
        SkillCategory.DAMAGE -> CharSkill.CommonDamage(
            id = id,
            name = name,
            description = description,
            refreshTime = refreshTime,
            minLevel = minLevel,
            imageName = imageName,
            attributes = attributes,
            damage = damage ?: 0L
        )

        SkillCategory.DAMAGE_OVER_TIME -> CharSkill.DamageOverTime(
            id = id,
            name = name,
            description = description,
            refreshTime = refreshTime,
            minLevel = minLevel,
            imageName = imageName,
            attributes = attributes,
            damage = damage ?: 0L,
            duration = duration ?: 0
        )

        SkillCategory.VAMPIRIC_DAMAGE -> CharSkill.VampiricDamage(
            id = id,
            name = name,
            description = description,
            refreshTime = refreshTime,
            minLevel = minLevel,
            imageName = imageName,
            attributes = attributes,
            damage = damage ?: 0L,
            multiplier = multiplier ?: 0.0
        )

        SkillCategory.OFFENSIVE_BUFF,
        SkillCategory.DEFENSIVE_BUFF -> CharSkill.Buff(
            id = id,
            name = name,
            description = description,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            minLevel = minLevel,
            imageName = imageName,
            attributes = attributes,
            multiplier = multiplier ?: 0.0,
            duration = duration ?: 0
        )

        SkillCategory.OFFENSIVE_DEBUFF,
        SkillCategory.DEFENSIVE_DEBUFF -> CharSkill.Debuff(
            id = id,
            name = name,
            description = description,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            minLevel = minLevel,
            imageName = imageName,
            attributes = attributes,
            multiplier = multiplier ?: 0.0,
            duration = duration ?: 0,
            damage = damage
        )
    }
}

fun CharSkillTuple.toDomainDetails() = CharSkillDetails(
    id = id,
    name = name,
    description = description,
    skillCategory = skillCategory,
    damage = damage,
    multiplier = multiplier,
    duration = duration,
    refreshTime = refreshTime,
    minLevel = minLevel,
    attributes = CharSkill.Attributes(
        requiredStrength = requiredStrength,
        requiredDexterity = requiredDexterity,
        requiredIntelligence = requiredIntelligence,
        requiredPhysicalResistance = requiredPhysicalResistance,
        requiredMagicResistance = requiredMagicResistance,
        requiredVitality = requiredVitality,
        requiredAgility = requiredAgility
    ),
    imageName = imageName,
    blocked = false
)

fun MobSkillTuple.toDomain(): MobSkill {
    return when (skillCategory) {
        SkillCategory.DAMAGE -> MobSkill.CommonDamage(
            id = id,
            name = name,
            description = description,
            imageName = imageName,
            refreshTime = refreshTime,
            minLevel = minLevel,
            damage = damage ?: 0L
        )

        SkillCategory.DAMAGE_OVER_TIME -> MobSkill.DamageOverTime(
            id = id,
            name = name,
            description = description,
            imageName = imageName,
            refreshTime = refreshTime,
            minLevel = minLevel,
            damage = damage ?: 0L,
            duration = duration ?: 0
        )

        SkillCategory.VAMPIRIC_DAMAGE -> MobSkill.VampiricDamage(
            id = id,
            name = name,
            description = description,
            imageName = imageName,
            refreshTime = refreshTime,
            minLevel = minLevel,
            damage = damage ?: 0L,
            multiplier = multiplier ?: 0.0
        )

        SkillCategory.OFFENSIVE_BUFF,
        SkillCategory.DEFENSIVE_BUFF -> MobSkill.Buff(
            id = id,
            name = name,
            description = description,
            imageName = imageName,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            minLevel = minLevel,
            multiplier = multiplier ?: 0.0,
            duration = duration ?: 0
        )

        SkillCategory.OFFENSIVE_DEBUFF,
        SkillCategory.DEFENSIVE_DEBUFF -> MobSkill.Debuff(
            id = id,
            name = name,
            description = description,
            imageName = imageName,
            skillCategory = skillCategory,
            refreshTime = refreshTime,
            minLevel = minLevel,
            multiplier = multiplier ?: 0.0,
            duration = duration ?: 0
        )
    }
}
