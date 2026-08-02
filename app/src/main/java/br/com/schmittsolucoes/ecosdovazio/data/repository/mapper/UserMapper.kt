package br.com.schmittsolucoes.ecosdovazio.data.repository.mapper

import br.com.schmittsolucoes.ecosdovazio.data.model.UserEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.User

fun User.toEntity() = UserEntity(
    id = id
)

fun UserEntity.toDomain() = User(
    id = id
)
