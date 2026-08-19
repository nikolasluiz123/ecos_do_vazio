package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.BASE_EXPERIENCE_VALUE
import kotlin.math.pow
import kotlin.math.roundToLong

class CalculateNextLevelExperienceUseCase {
    fun executeInternal(level: Long): Long {
        val factor = getExperienceFactor(level)
        return (BASE_EXPERIENCE_VALUE * level.toDouble().pow(factor)).roundToLong()
    }

    private fun getExperienceFactor(level: Long): Double {
        return when (level) {
            in 2..10 -> 1.5
            in 11..15 -> 1.8
            in 16..25 -> 2.2
            in 26..30 -> 2.5
            else -> 3.0
        }
    }
}
