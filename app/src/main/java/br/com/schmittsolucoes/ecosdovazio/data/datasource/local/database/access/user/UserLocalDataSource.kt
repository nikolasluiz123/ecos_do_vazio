package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.user

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.EntityLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.UserEntity

interface UserLocalDataSource: EntityLocalDataSource<UserEntity> {
    suspend fun getExistsUser(): Boolean
    suspend fun getFirstUser(): UserEntity
}