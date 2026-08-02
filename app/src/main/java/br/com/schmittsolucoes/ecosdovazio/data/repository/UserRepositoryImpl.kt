package br.com.schmittsolucoes.ecosdovazio.data.repository

import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.repository.mapper.toEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.User
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userLocalDataSource: UserLocalDataSource,
): UserRepository {
    override suspend fun insert(user: User) {
        userLocalDataSource.insert(listOf(user.toEntity()))
    }

    override suspend fun getExistsUser(): Boolean {
        return userLocalDataSource.getExistsUser()
    }
}