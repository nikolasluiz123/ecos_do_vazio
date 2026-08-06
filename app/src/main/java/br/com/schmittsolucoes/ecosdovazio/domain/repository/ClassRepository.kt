package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.ClassSelection

import kotlinx.coroutines.flow.Flow

interface ClassRepository {
    suspend fun save(classes: List<Class>)
    suspend fun getExistsClass(): Boolean
    fun getClassesForSelection(languageTag: String): Flow<List<ClassSelection>>
}
