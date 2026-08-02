package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Language

interface LanguageRepository {
    suspend fun save(languages: List<Language>)
    suspend fun getExistsLanguage(): Boolean
}