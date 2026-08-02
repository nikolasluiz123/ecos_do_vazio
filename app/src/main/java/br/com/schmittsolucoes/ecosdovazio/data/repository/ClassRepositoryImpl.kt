package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes.ClassLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.Class
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import javax.inject.Inject

class ClassRepositoryImpl @Inject constructor(
    private val classLocalDataSource: ClassLocalDataSource,
): ClassRepository {
    override suspend fun save(classes: List<Class>) {
        val classEntities = classes.map { it.toEntity() }
        classLocalDataSource.upsert(classEntities)
    }

    override suspend fun getExistsClass(): Boolean {
        return classLocalDataSource.getExistsClass()
    }
}