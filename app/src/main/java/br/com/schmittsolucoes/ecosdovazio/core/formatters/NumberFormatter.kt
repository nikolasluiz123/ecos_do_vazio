package br.com.schmittsolucoes.ecosdovazio.core.formatters

import kotlin.math.roundToInt

object NumberFormatter {

    fun formatPercentage(value: Double): String {
        return "${(value * 100).roundToInt()}%"
    }
}
