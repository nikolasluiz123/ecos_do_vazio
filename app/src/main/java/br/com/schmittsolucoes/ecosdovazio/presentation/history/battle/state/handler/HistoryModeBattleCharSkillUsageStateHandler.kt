package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.UseCharSkillUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.allMobsIsDead
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.getMobById
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.incrementRound
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.updateSkillRefreshTime
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

        val selectedMob = uiState.selectedMob

        if (selectedMob == null && skill.skillCategory != SkillCategory.HEAL) {
             return CharSkillUsageExecutionResult.NoSelectedMob
        }

        val char = uiState.char ?: return CharSkillUsageExecutionResult.Ignored

        val result = useCharSkillUseCase(
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skillUIModel = skill),
            battleCharInfo = battleInfoMapper.mapToDomainInfo(charUIModel = char),
            mobs = uiState.mobs.map { battleInfoMapper.mapToDomainInfo(mobUIModel = it) },
            selectedMobId = selectedMob?.phaseMobId,
        )

        var updatedState = currentState

        when (result) {
            is CharSkillUsageResult.CommonDamage -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob!!,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = updatedState.incrementRound()
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
                updatedState = updatedState.incrementRound()
            }

            is CharSkillUsageResult.DamageOverTime -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob!!,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = mobsActiveStatusStateHandler.registerMobDot(
                    currentState = updatedState,
                    selectedMob = selectedMob,
                    skill = skill,
                    result = result,
                )
                updatedState = updatedState.incrementRound()
            }

            is CharSkillUsageResult.Debuff -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob!!,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = mobsActiveStatusStateHandler.registerMobDebuff(
                    currentState = updatedState,
                    selectedMob = selectedMob,
                    skill = skill,
                    result = result,
                )

                if (uiState.allMobsIsDead(currentState = updatedState)) {
                    updatedState = updatedState.incrementRound()
                }
            }

            is CharSkillUsageResult.VampiricDamage -> {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = selectedMob!!,
                    newEnemyHealth = result.newEnemyHealth,
                )
                updatedState = charHealthStateHandler.updateCharHealth(
                    currentState = updatedState,
                    newHealth = result.newCharHealth,
                )
                updatedState = updatedState.incrementRound()
            }

            is CharSkillUsageResult.Buff -> {
                updatedState = charActiveStatusStateHandler.registerCharBuff(
                    currentState = updatedState,
                    skill = skill,
                    result = result,
                )
            }

            is CharSkillUsageResult.Heal -> {
                updatedState = charHealthStateHandler.updateCharHealth(
                    currentState = updatedState,
                    newHealth = result.newCharHealth,
                )
                updatedState = updatedState.incrementRound()
            }
        }

        updatedState = updatedState.updateSkillRefreshTime(
            skillId = skill.id,
            refreshTime = result.refreshTime,
        )

        return CharSkillUsageExecutionResult.Executed(newState = updatedState)
    }
}
