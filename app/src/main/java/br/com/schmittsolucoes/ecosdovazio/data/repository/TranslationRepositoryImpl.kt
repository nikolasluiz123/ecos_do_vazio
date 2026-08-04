package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.TranslationLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Translation
import br.com.schmittsolucoes.ecosdovazio.domain.repository.TranslationRepository
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val translationLocalDataSource: TranslationLocalDataSource
): TranslationRepository {
    override suspend fun save(translations: List<Translation>) {
        translationLocalDataSource.upsert(translations.map { it.toEntity() })
    }

    override suspend fun saveIdentifiers(identifiers: List<String>) {
        translationLocalDataSource.upsertIdentifiers(identifiers.map { TranslationIdentifierEntity(it) })
    }

    override suspend fun getExistsTranslation(): Boolean {
        return translationLocalDataSource.getExistsTranslation()
    }
}