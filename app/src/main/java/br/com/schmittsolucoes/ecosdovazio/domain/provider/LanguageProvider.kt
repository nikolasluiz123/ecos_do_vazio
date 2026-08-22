package br.com.schmittsolucoes.ecosdovazio.domain.provider

interface LanguageProvider {
    fun getTag(language: String, region: String): String
    fun getDeviceTag(): String
    fun getDeviceLanguage(): String
    fun getDeviceRegion(): String
    fun getString(resourceId: Int, languageTag: String): String
}
