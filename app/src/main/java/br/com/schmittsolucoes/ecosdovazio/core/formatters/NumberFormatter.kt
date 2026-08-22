package br.com.schmittsolucoes.ecosdovazio.core.formatters

import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

interface NumberFormatter {
    fun formatPercentage(value: Double): String
}

class LocalizableNumberFormatter @Inject constructor(
    private val languageProvider: LanguageProvider
) : NumberFormatter {

    override fun formatPercentage(value: Double): String {
        val locale = Locale.Builder()
            .setLanguage(languageProvider.getDeviceLanguage())
            .setRegion(languageProvider.getDeviceRegion())
            .build()

        val formatter = NumberFormat.getPercentInstance(locale).apply {
            minimumFractionDigits = 2
            maximumFractionDigits = 2
        }

        return formatter.format(value)
    }
}
