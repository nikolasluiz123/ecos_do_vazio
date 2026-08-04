package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Translation

interface TranslationRepository {
    suspend fun save(translations: List<Translation>)
    suspend fun saveIdentifiers(identifiers: List<String>)
    suspend fun getExistsTranslation(): Boolean
}