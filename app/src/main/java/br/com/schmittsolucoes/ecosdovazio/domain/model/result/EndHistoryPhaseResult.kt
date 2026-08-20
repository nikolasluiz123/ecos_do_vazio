package br.com.schmittsolucoes.ecosdovazio.domain.model.result

data class EndHistoryPhaseResult(
    val isHistoryFinished: Boolean,
    val levelInfo: LevelInfo = LevelInfo()
) {
    data class LevelInfo(
        val currentLevel: Long = 0,
        val levelUp: Boolean = false
    )
}