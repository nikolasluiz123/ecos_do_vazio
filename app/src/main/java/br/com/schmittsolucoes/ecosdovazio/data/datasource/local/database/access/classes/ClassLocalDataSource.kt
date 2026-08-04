package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.ClassEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.ClassSelectionTuple

import kotlinx.coroutines.flow.Flow

interface ClassLocalDataSource: EntityLocalDataSource<ClassEntity> {
    suspend fun getExistsClass(): Boolean
    fun getClassesForSelection(languageTag: String): Flow<List<ClassSelectionTuple>>
}
