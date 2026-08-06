package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import kotlinx.coroutines.flow.Flow

interface CharRepository {
    suspend fun insert(char: Char)
    suspend fun getExistsByName(name: String): Boolean
    fun getUserChars(userId: String): Flow<List<CharSelection>>
}