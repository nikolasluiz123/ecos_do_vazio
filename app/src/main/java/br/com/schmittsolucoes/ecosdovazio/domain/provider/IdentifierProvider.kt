package br.com.schmittsolucoes.ecosdovazio.domain.provider

interface IdentifierProvider {
    fun generate(): String
}