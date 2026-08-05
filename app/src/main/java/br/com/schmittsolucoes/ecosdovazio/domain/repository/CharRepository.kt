package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.Char

interface CharRepository {
    suspend fun insert(char: Char)
    suspend fun getExistsByName(name: String): Boolean
}