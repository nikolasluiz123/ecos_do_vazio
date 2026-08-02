package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.LanguageLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Language
import br.com.schmittsolucoes.ecosdovazio.domain.repository.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val languageLocalDataSource: LanguageLocalDataSource
): LanguageRepository {
    override suspend fun save(languages: List<Language>) {
        val languageEntities = languages.map { it.toEntity() }
        languageLocalDataSource.upsert(languageEntities)
    }

    override suspend fun getExistsLanguage(): Boolean {
        return languageLocalDataSource.getExistsLanguage()
    }
}