package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.RunEnemyRoundUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.EndHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.StartHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.allMobsIsDead
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.charIsDead
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.decrementSkillsRefreshTime
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.incrementRound
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

        if (uiState.allMobsIsDead(currentState = currentState) || uiState.charIsDead(currentState = currentState)) {
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

            if (!uiState.allMobsIsDead(currentState = updatedState)) {
                updatedState = updatedState.incrementRound()
            }
        } else {
            updatedState = updatedState.decrementSkillsRefreshTime()
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
            if (uiState.allMobsIsDead(currentState = currentState)) {
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

    private data class FinishBattleResult(
        val newState: HistoryModeBattleInternalState,
        val finishResult: PhaseFinishResult? = null,
    )
}
