package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.getMobById
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.getMobSkill
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.getSelectedMobOrFirst
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.updateSkillRefreshTime
import javax.inject.Inject

class HistoryModeBattleMobSkillUsageStateHandler @Inject constructor(
    private val charHealthStateHandler: HistoryModeBattleCharHealthStateHandler,
    private val mobsHealthStateHandler: HistoryModeBattleMobsHealthStateHandler,
    private val charActiveStatusStateHandler: HistoryModeBattleCharActiveStatusStateHandler,
    private val mobsActiveStatusStateHandler: HistoryModeBattleMobsActiveStatusStateHandler,
) {

    fun handleMobSkillResult(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        result: MobSkillUsageResult,
    ): HistoryModeBattleInternalState {
        var updatedState = currentState

        when (result) {
            is MobSkillUsageResult.CommonDamage -> {
                updatedState = charHealthStateHandler.updateCharHealth(
                    currentState = updatedState,
                    newHealth = result.newEnemyHealth,
                )
            }

            is MobSkillUsageResult.DamageOverTime -> {
                updatedState = charHealthStateHandler.updateCharHealth(
                    currentState = updatedState,
                    newHealth = result.newEnemyHealth,
                )
                updatedState = registerCharDot(
                    currentState = updatedState,
                    uiState = uiState,
                    result = result,
                )
            }

            is MobSkillUsageResult.Debuff -> {
                updatedState = charHealthStateHandler.updateCharHealth(
                    currentState = updatedState,
                    newHealth = result.newEnemyHealth,
                )
                updatedState = registerCharDebuff(
                    currentState = updatedState,
                    uiState = uiState,
                    result = result,
                )
            }

            is MobSkillUsageResult.VampiricDamage -> {
                updatedState = charHealthStateHandler.updateCharHealth(
                    currentState = updatedState,
                    newHealth = result.newEnemyHealth,
                )
                val mob = uiState.getMobById(id = result.mobId)
                if (mob != null) {
                    updatedState = mobsHealthStateHandler.updateMobHealth(
                        currentState = updatedState,
                        mobs = uiState.mobs,
                        mobToUpdate = mob,
                        newEnemyHealth = result.newCharHealth,
                    )
                }
            }

            is MobSkillUsageResult.Buff -> {
                updatedState = registerMobBuff(
                    currentState = updatedState,
                    uiState = uiState,
                    result = result,
                )
            }

            is MobSkillUsageResult.Heal -> {
                val mob = uiState.getMobById(id = result.targetMobId)
                if (mob != null) {
                    updatedState = mobsHealthStateHandler.updateMobHealth(
                        currentState = updatedState,
                        mobs = uiState.mobs,
                        mobToUpdate = mob,
                        newEnemyHealth = result.newMobHealth,
                    )
                }
            }

            is MobSkillUsageResult.AreaHeal -> {
                result.newMobsHealth.forEach { (phaseMobId, newHealth) ->
                    val mob = uiState.getMobById(id = phaseMobId) ?: return@forEach
                    updatedState = mobsHealthStateHandler.updateMobHealth(
                        currentState = updatedState,
                        mobs = uiState.mobs,
                        mobToUpdate = mob,
                        newEnemyHealth = newHealth,
                    )
                }
            }
        }

        return updatedState.updateSkillRefreshTime(
            skillId = result.skillId,
            refreshTime = result.refreshTime,
        )
    }

    private fun registerMobBuff(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        result: MobSkillUsageResult.Buff,
    ): HistoryModeBattleInternalState {
        val mob = uiState.getMobById(id = result.mobId) ?: return currentState
        val skill = mob.getMobSkill(skillId = result.skillId) ?: return currentState

        return mobsActiveStatusStateHandler.registerMobBuff(
            currentState = currentState,
            mob = mob,
            skill = skill,
            result = result,
        )
    }

    private fun registerCharDot(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        result: MobSkillUsageResult.DamageOverTime,
    ): HistoryModeBattleInternalState {
        val mob = uiState.getSelectedMobOrFirst(selectedMobId = currentState.selectedMobId) ?: return currentState
        val skill = mob.getMobSkill(skillId = result.skillId) ?: return currentState

        return charActiveStatusStateHandler.registerCharDot(
            currentState = currentState,
            mob = mob,
            skill = skill,
            result = result,
        )
    }

    private fun registerCharDebuff(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        result: MobSkillUsageResult.Debuff,
    ): HistoryModeBattleInternalState {
        val mob = uiState.getSelectedMobOrFirst(selectedMobId = currentState.selectedMobId) ?: return currentState
        val skill = mob.getMobSkill(skillId = result.skillId) ?: return currentState

        return charActiveStatusStateHandler.registerCharDebuff(
            currentState = currentState,
            mob = mob,
            skill = skill,
            result = result,
        )
    }
}
