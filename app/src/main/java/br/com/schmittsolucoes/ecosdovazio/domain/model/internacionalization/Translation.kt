package br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

data class Translation(
    val id: TranslationIdentifier,
    val languageId: String,
    val translatedText: String,
)
