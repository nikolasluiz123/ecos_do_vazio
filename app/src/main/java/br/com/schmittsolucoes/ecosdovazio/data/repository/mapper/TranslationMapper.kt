package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Translation

fun Translation.toEntity() = TranslationEntity(
    id = id.name,
    languageId = languageId,
    translatedText = translatedText
)

fun TranslationEntity.toDomain() = Translation(
    id = br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier.valueOf(id),
    languageId = languageId,
    translatedText = translatedText
)
