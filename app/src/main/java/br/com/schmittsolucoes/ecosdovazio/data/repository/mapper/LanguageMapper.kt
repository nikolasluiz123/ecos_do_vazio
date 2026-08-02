package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.LanguageEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Language

fun Language.toEntity() = LanguageEntity(
    id = id,
    isDefault = isDefault
)

fun LanguageEntity.toDomain() = Language(
    id = id,
    isDefault = isDefault
)
