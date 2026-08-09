package br.com.schmittsolucoes.ecosdovazio.data.provider

import android.content.Context
import android.content.res.Configuration
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class LocaleLanguageProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : LanguageProvider {

    private val contextCache = mutableMapOf<String, Context>()

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

    override fun getString(resourceId: Int, languageTag: String): String {
        val localizedContext = contextCache.getOrPut(languageTag) {
            val locale = Locale.forLanguageTag(languageTag)
            val configuration = Configuration(context.resources.configuration).apply {
                setLocale(locale)
            }
            context.createConfigurationContext(configuration)
        }

        return localizedContext.getString(resourceId)
    }
}
