package br.com.schmittsolucoes.ecosdovazio.domain.provider

interface ResourcesProvider {
    fun getClassImage(name: String): Int?
    fun getBattleClassImage(name: String): Int?
    fun getProfileClassImage(name: String): Int?
}
