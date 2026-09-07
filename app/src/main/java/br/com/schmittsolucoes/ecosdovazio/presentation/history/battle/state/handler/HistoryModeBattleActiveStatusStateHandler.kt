package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleInfoMapper
import javax.inject.Inject

class HistoryModeBattleActiveStatusStateHandler @Inject constructor(
    private val charActiveStatusStateHandler: HistoryModeBattleCharActiveStatusStateHandler,
    private val mobsActiveStatusStateHandler: HistoryModeBattleMobsActiveStatusStateHandler,
) {

    fun applyDoTsDamage(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        battleInfoMapper: BattleInfoMapper,
    ): HistoryModeBattleInternalState {
        val char = uiState.char ?: return currentState
        val charInfo = battleInfoMapper.mapToDomainInfo(char)
        val mobsInfo = uiState.mobs.associate { it.phaseMobId to battleInfoMapper.mapToDomainInfo(it) }

        var state = mobsActiveStatusStateHandler.applyMobsDoTDamage(currentState, uiState, charInfo, mobsInfo)
        state = charActiveStatusStateHandler.applyCharDoTDamage(state, uiState, charInfo, mobsInfo)

        return state
    }

    fun applyDebuffs(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        battleInfoMapper: BattleInfoMapper,
    ): HistoryModeBattleInternalState {
        val char = uiState.char ?: return currentState
        val charInfo = battleInfoMapper.mapToDomainInfo(char)
        val mobsInfo = uiState.mobs.associate { it.phaseMobId to battleInfoMapper.mapToDomainInfo(it) }

        var state = mobsActiveStatusStateHandler.applyMobsDebuff(currentState, uiState, charInfo, mobsInfo)
        state = charActiveStatusStateHandler.applyCharDebuff(state, uiState, charInfo, mobsInfo)

        return state
    }

    fun applyBuffs(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        battleInfoMapper: BattleInfoMapper,
    ): HistoryModeBattleInternalState {
        val char = uiState.char ?: return currentState
        val charInfo = battleInfoMapper.mapToDomainInfo(char)
        val mobsInfo = uiState.mobs.associate { it.phaseMobId to battleInfoMapper.mapToDomainInfo(it) }

        var state = mobsActiveStatusStateHandler.applyMobsBuff(currentState, uiState, charInfo, mobsInfo)
        state = charActiveStatusStateHandler.applyCharBuff(state, uiState, charInfo, mobsInfo)

        return state
    }

    fun applyAllTurnStatuses(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        battleInfoMapper: BattleInfoMapper,
    ): HistoryModeBattleInternalState {
        var state = applyDoTsDamage(currentState, uiState, battleInfoMapper)
        state = applyDebuffs(state, uiState, battleInfoMapper)
        state = applyBuffs(state, uiState, battleInfoMapper)

        return state
    }
}
