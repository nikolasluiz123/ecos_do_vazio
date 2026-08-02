package br.com.schmittsolucoes.ecosdovazio.domain.provider

interface LanguageProvider {
    fun getTag(language: String, region: String): String
    fun getDeviceTag(): String
}
