package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.ClassSelection

import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    suspend fun save(classes: List<Class>)
    suspend fun getExistsClass(): Boolean
    fun getClassesForSelection(languageTag: String): Flow<List<ClassSelection>>
}
