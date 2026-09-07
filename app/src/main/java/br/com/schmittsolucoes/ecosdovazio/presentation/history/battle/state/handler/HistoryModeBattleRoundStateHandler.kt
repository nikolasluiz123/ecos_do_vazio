package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.RunEnemyRoundUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.EndHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.StartHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleInfoMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleMapper
import javax.inject.Inject

sealed interface PhaseFinishResult {
    data class Victory(val levelUp: Boolean, val currentLevel: Long) : PhaseFinishResult
}

data class RoundUpdateExecutionResult(
    val newState: HistoryModeBattleInternalState,
    val isPhaseStarted: Boolean,
    val finishResult: PhaseFinishResult? = null,
)

class HistoryModeBattleRoundStateHandler @Inject constructor(
    private val startHistoryPhaseUseCase: StartHistoryPhaseUseCase,
    private val endHistoryPhaseUseCase: EndHistoryPhaseUseCase,
    private val runEnemyRoundUseCase: RunEnemyRoundUseCase,
    private val battleInfoMapper: BattleInfoMapper,
    private val battleMapper: BattleMapper,
    private val activeStatusStateHandler: HistoryModeBattleActiveStatusStateHandler,
    private val mobSkillUsageStateHandler: HistoryModeBattleMobSkillUsageStateHandler,
) {

    suspend fun executeRoundUpdate(
        phaseId: String,
        isPhaseStarted: Boolean,
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
    ): RoundUpdateExecutionResult {
        var phaseStarted = isPhaseStarted

        if (!phaseStarted) {
            startHistoryPhaseUseCase(phaseId = phaseId)
            phaseStarted = true
        }

        if (allMobsIsDead(state = currentState, uiState = uiState) || charIsDead(state = currentState, uiState = uiState)) {
            val finishState = tryFinishBattle(
                phaseId = phaseId,
                currentState = currentState,
                uiState = uiState,
            )
            return RoundUpdateExecutionResult(
                newState = finishState.newState,
                isPhaseStarted = phaseStarted,
                finishResult = finishState.finishResult,
            )
        }

        var updatedState = activeStatusStateHandler.applyAllTurnStatuses(
            currentState = currentState,
            uiState = uiState,
            battleInfoMapper = battleInfoMapper,
        )

        if (isEnemyRound(actualRound = uiState.actualRound)) {
            runEnemyRoundUseCase(
                getCharInfo = { battleInfoMapper.mapToDomainInfo(charUIModel = uiState.char!!) },
                mobs = uiState.mobs.map { battleMapper.mapToDomain(mobUIModel = it) },
                onMobUseSkill = { result ->
                    updatedState = mobSkillUsageStateHandler.handleMobSkillResult(
                        currentState = updatedState,
                        uiState = uiState,
                        result = result,
                    )
                },
            )

            if (!allMobsIsDead(state = updatedState, uiState = uiState)) {
                updatedState = incrementRound(state = updatedState)
            }
        } else {
            updatedState = decrementSkillsRefreshTime(state = updatedState)
        }

        val finishState = tryFinishBattle(
            phaseId = phaseId,
            currentState = updatedState,
            uiState = uiState,
        )

        return RoundUpdateExecutionResult(
            newState = finishState.newState,
            isPhaseStarted = phaseStarted,
            finishResult = finishState.finishResult,
        )
    }

    private suspend fun tryFinishBattle(
        phaseId: String,
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
    ): FinishBattleResult {
        if (currentState.shouldPop) return FinishBattleResult(newState = currentState)

        val char = uiState.char ?: return FinishBattleResult(newState = currentState)

        val result = endHistoryPhaseUseCase(
            phaseId = phaseId,
            battleCharInfo = battleInfoMapper.mapToDomainInfo(charUIModel = char),
            mobs = uiState.mobs.map { battleInfoMapper.mapToDomainInfo(mobUIModel = it) },
        )

        var finishResult: PhaseFinishResult? = null

        if (result.isHistoryFinished) {
            if (allMobsIsDead(state = currentState, uiState = uiState)) {
                finishResult = PhaseFinishResult.Victory(
                    levelUp = result.levelInfo.levelUp,
                    currentLevel = result.levelInfo.currentLevel,
                )
            }

            return FinishBattleResult(
                newState = currentState.copy(shouldPop = true),
                finishResult = finishResult,
            )
        }

        return FinishBattleResult(newState = currentState)
    }

    fun isEnemyRound(actualRound: Long): Boolean {
        return actualRound % 2 == 0L
    }

    private fun incrementRound(state: HistoryModeBattleInternalState): HistoryModeBattleInternalState {
        return state.copy(actualRound = state.actualRound + 1)
    }

    private fun decrementSkillsRefreshTime(state: HistoryModeBattleInternalState): HistoryModeBattleInternalState {
        val updatedMap = state.skillsRefreshTime.mapValues { (_, time) ->
            if (time > 0) time - 1 else 0
        }.filterValues { it > 0 }

        return state.copy(skillsRefreshTime = updatedMap)
    }

    private fun charIsDead(state: HistoryModeBattleInternalState, uiState: HistoryModeBattleUIState): Boolean {
        val notLoaded = state.charHealth == null && uiState.char?.actualHealth == null
        if (notLoaded) return false

        return (state.charHealth ?: uiState.char?.actualHealth ?: 0) <= 0
    }

    private fun allMobsIsDead(state: HistoryModeBattleInternalState, uiState: HistoryModeBattleUIState): Boolean {
        val notLoaded = state.mobsHealth.isEmpty() && uiState.mobs.all { it.actualHealth <= 0 }
        if (notLoaded) return false

        val mobsHealth = state.mobsHealth.ifEmpty {
            uiState.mobs.associate { it.phaseMobId to it.actualHealth }
        }

        return mobsHealth.all { it.value <= 0 }
    }

    private data class FinishBattleResult(
        val newState: HistoryModeBattleInternalState,
        val finishResult: PhaseFinishResult? = null,
    )
}
