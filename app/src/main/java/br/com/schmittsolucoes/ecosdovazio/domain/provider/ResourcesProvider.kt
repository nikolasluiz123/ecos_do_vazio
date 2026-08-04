package br.com.schmittsolucoes.ecosdovazio.domain.provider

interface ResourcesProvider {
    fun getClassImage(name: String): Int?
}
