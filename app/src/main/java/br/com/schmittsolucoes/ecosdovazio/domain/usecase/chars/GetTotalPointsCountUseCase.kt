package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

class GetTotalPointsCountUseCase {
    fun executeInternal(level: Long): Long {
        var totalPoints = 0L

        for (i in 1..level) {
            totalPoints += getPointsForLevel(i)
        }

        return totalPoints
    }

    private fun getPointsForLevel(level: Long): Long {
        return when (level) {
            in 0..1 -> 0L
            in 2..10 -> 2L
            in 11..20 -> 3L
            in 21..30 -> 5L
            else -> 6L
        }
    }
}