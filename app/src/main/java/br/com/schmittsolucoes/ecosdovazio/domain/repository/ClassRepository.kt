package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.Class

interface ClassRepository {
    suspend fun save(classes: List<Class>)
    suspend fun getExistsClass(): Boolean
}