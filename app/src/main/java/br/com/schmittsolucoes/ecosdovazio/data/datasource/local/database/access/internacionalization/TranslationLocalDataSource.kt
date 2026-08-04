package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity

interface TranslationLocalDataSource: EntityLocalDataSource<TranslationEntity> {
    suspend fun getExistsTranslation(): Boolean
    suspend fun upsertIdentifiers(identifiers: List<TranslationIdentifierEntity>)
}
