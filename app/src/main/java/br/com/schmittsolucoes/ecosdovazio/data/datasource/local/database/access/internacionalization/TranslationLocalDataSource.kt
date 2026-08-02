package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationEntity

interface TranslationLocalDataSource: EntityLocalDataSource<TranslationEntity> {
    suspend fun getExistsTranslation(): Boolean
}
