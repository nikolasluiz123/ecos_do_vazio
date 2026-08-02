package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    override val id: String
): UniqueEntity
