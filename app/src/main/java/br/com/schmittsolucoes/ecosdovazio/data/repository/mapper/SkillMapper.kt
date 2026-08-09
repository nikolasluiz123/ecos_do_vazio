package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.SkillEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
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
    imageName = imageName
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
    imageName = imageName
)
