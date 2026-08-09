package br.com.schmittsolucoes.ecosdovazio.core.database.transaction

interface DatabaseTransaction {
    suspend fun <T> run(block: suspend () -> T): T
    fun isInTransaction(): Boolean
}