package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.UseCharSkillUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.getMobById
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleInfoMapper
import javax.inject.Inject

sealed interface CharSkillUsageExecutionResult {
    data class Executed(val newState: HistoryModeBattleInternalState) : CharSkillUsageExecutionResult
    data object NoSelectedMob : CharSkillUsageExecutionResult
    data object Ignored : CharSkillUsageExecutionResult
}

class HistoryModeBattleCharSkillUsageStateHandler @Inject constructor(
    private val useCharSkillUseCase: UseCharSkillUseCase,
    private val battleInfoMapper: BattleInfoMapper,
    private val charHealthStateHandler: HistoryModeBattleCharHealthStateHandler,
    private val mobsHealthStateHandler: HistoryModeBattleMobsHealthStateHandler,
    private val charActiveStatusStateHandler: HistoryModeBattleCharActiveStatusStateHandler,
    private val mobsActiveStatusStateHandler: HistoryModeBattleMobsActiveStatusStateHandler,
) {

    fun executeSkillUsage(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        skill: CharSkillUIModel,
    ): CharSkillUsageExecutionResult {
        if (skill.currentRefreshTime > 0 || skill.blocked) return CharSkillUsageExecutionResult.Ignored

        val selectedMob = uiState.selectedMob ?: return CharSkillUsageExecutionResult.NoSelectedMob
        val char = uiState.char ?: return CharSkillUsageExecutionResult.Ignored

        val result = useCharSkillUseCase(
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skillUIModel = skill),
            battleCharInfo = battleInfoMapper.mapToDomainInfo(charUIModel = char),
            mobs = uiState.mobs.map { battleInfoMapper.mapToDomainInfo(mobUIModel = it) },
            selectedMobId = selectedMob.phaseMobId,
        )

        var updatedState = currentState

        when (result) {
            is CharSkillUsageResult.CommonDamage -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = incrementRound(state = updatedState)
            }

            is CharSkillUsageResult.AreaDamage -> {
                result.newEnemyHealths.forEach { (phaseMobId, newHealth) ->
                    val mob = uiState.getMobById(id = phaseMobId) ?: return@forEach
                    updatedState = mobsHealthStateHandler.updateMobHealth(
                        currentState = updatedState,
                        mobs = uiState.mobs,
                        mobToUpdate = mob,
                        newEnemyHealth = newHealth,
                    )
                }
                updatedState = incrementRound(state = updatedState)
            }

            is CharSkillUsageResult.DamageOverTime -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = mobsActiveStatusStateHandler.registerMobDot(
                    currentState = updatedState,
                    selectedMob = selectedMob,
                    skill = skill,
                    result = result,
                )
                updatedState = incrementRound(state = updatedState)
            }

            is CharSkillUsageResult.Debuff -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = mobsActiveStatusStateHandler.registerMobDebuff(
                    currentState = updatedState,
                    selectedMob = selectedMob,
                    skill = skill,
                    result = result,
                )

                if (allMobsIsDead(state = updatedState, uiState = uiState)) {
                    updatedState = incrementRound(state = updatedState)
                }
            }

            is CharSkillUsageResult.VampiricDamage -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = charHealthStateHandler.updateCharHealth(
                    currentState = updatedState,
                    newHealth = result.newCharHealth,
                )
                updatedState = incrementRound(state = updatedState)
            }

            is CharSkillUsageResult.Buff -> {
                updatedState = charActiveStatusStateHandler.registerCharBuff(
                    currentState = updatedState,
                    skill = skill,
                    result = result,
                )
            }
        }

        updatedState = updateSkillRefreshTime(
            state = updatedState,
            skillId = skill.id,
            refreshTime = result.refreshTime,
        )

        return CharSkillUsageExecutionResult.Executed(newState = updatedState)
    }

    private fun updateSkillRefreshTime(
        state: HistoryModeBattleInternalState,
        skillId: String,
        refreshTime: Int,
    ): HistoryModeBattleInternalState {
        return state.copy(skillsRefreshTime = state.skillsRefreshTime + (skillId to refreshTime))
    }

    private fun incrementRound(state: HistoryModeBattleInternalState): HistoryModeBattleInternalState {
        return state.copy(actualRound = state.actualRound + 1)
    }

    private fun allMobsIsDead(state: HistoryModeBattleInternalState, uiState: HistoryModeBattleUIState): Boolean {
        val notLoaded = state.mobsHealth.isEmpty() && uiState.mobs.all { it.actualHealth <= 0 }
        if (notLoaded) return false

        val mobsHealth = state.mobsHealth.ifEmpty {
            uiState.mobs.associate { it.phaseMobId to it.actualHealth }
        }

        return mobsHealth.all { it.value <= 0 }
    }
}
