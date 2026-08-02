package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.ClassEntity

interface ClassLocalDataSource: EntityLocalDataSource<ClassEntity> {
    suspend fun getExistsClass(): Boolean
}