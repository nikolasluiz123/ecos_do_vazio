package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.User

interface UserRepository {
    suspend fun insert(user: User)
    suspend fun getExistsUser(): Boolean
}