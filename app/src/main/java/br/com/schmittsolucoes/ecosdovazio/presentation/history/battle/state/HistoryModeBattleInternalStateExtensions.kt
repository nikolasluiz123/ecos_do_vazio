package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state

fun HistoryModeBattleInternalState.incrementRound(): HistoryModeBattleInternalState {
    return copy(actualRound = actualRound + 1)
}

fun HistoryModeBattleInternalState.updateSkillRefreshTime(
    skillId: String,
    refreshTime: Int,
): HistoryModeBattleInternalState {
    return copy(skillsRefreshTime = skillsRefreshTime + (skillId to refreshTime))
}

fun HistoryModeBattleInternalState.decrementSkillsRefreshTime(): HistoryModeBattleInternalState {
    val updatedMap = skillsRefreshTime.mapValues { (_, time) ->
        if (time > 0) time - 1 else 0
    }.filterValues { it > 0 }

    return copy(skillsRefreshTime = updatedMap)
}
