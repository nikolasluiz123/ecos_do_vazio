package br.com.schmittsolucoes.ecosdovazio.data.provider

import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import java.util.Locale
import javax.inject.Inject

class LocaleLanguageProvider @Inject constructor() : LanguageProvider {

    override fun getTag(language: String, region: String): String {
        return Locale.Builder()
            .setLanguage(language)
            .setRegion(region)
            .build()
            .toLanguageTag()
    }

    override fun getDeviceTag(): String {
        return Locale.getDefault().toLanguageTag()
    }
}
