package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

class GetPointsCountByLevelUseCase {
    fun executeInternal(level: Long): Long {
        return when (level) {
            in 2..10 -> 2L
            in 11..20 -> 3L
            in 21..30 -> 5L
            else -> 6L
        }
    }
}