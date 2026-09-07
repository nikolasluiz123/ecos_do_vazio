package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsBuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsDebuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsDoTUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleInfoMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleMapper
import javax.inject.Inject

class HistoryModeBattleMobsActiveStatusStateHandler @Inject constructor(
    private val applyMobsDoTUseCase: ApplyMobsDoTUseCase,
    private val applyMobsDebuffUseCase: ApplyMobsDebuffUseCase,
    private val applyMobsBuffUseCase: ApplyMobsBuffUseCase,
    private val battleMapper: BattleMapper,
    private val battleInfoMapper: BattleInfoMapper,
    private val mobsHealthStateHandler: HistoryModeBattleMobsHealthStateHandler,
) {

    fun registerMobDot(
        currentState: HistoryModeBattleInternalState,
        selectedMob: BattleMobUIModel,
        skill: CharSkillUIModel,
        result: CharSkillUsageResult.DamageOverTime,
    ): HistoryModeBattleInternalState {
        val newStatus = CharActiveStatusUIModel.DoTUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skillUIModel = skill) as UsedCharSkillInfo.DamageOverTime,
            skillImage = skill.image,
        )

        return registerMobActiveStatus(
            currentState = currentState,
            mob = selectedMob,
            newStatus = newStatus,
        )
    }

    fun registerMobDebuff(
        currentState: HistoryModeBattleInternalState,
        selectedMob: BattleMobUIModel,
        skill: CharSkillUIModel,
        result: CharSkillUsageResult.Debuff,
    ): HistoryModeBattleInternalState {
        val newStatus = CharActiveStatusUIModel.DebuffUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skillUIModel = skill) as UsedCharSkillInfo.Debuff,
            skillImage = skill.image,
            skillCategory = skill.skillCategory,
        )

        return registerMobActiveStatus(
            currentState = currentState,
            mob = selectedMob,
            newStatus = newStatus,
        )
    }

    fun registerMobBuff(
        currentState: HistoryModeBattleInternalState,
        mob: BattleMobUIModel,
        skill: MobSkillUIModel,
        result: MobSkillUsageResult.Buff,
    ): HistoryModeBattleInternalState {
        val newStatus = MobActiveStatusUIModel.BuffUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillImage = skill.image,
            sourceId = mob.phaseMobId,
            skillCategory = skill.skillCategory,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skillUIModel = skill) as UsedMobSkillInfo.Buff,
        )

        return registerMobActiveStatus(
            currentState = currentState,
            mob = mob,
            newStatus = newStatus,
        )
    }

    fun registerMobActiveStatus(
        currentState: HistoryModeBattleInternalState,
        mob: BattleMobUIModel,
        newStatus: ActiveStatusUIModel,
    ): HistoryModeBattleInternalState {
        val currentActiveStatus = currentState.mobsActiveStatus[mob.phaseMobId] ?: emptyList()

        return if (currentActiveStatus.none { it.skillId == newStatus.skillId }) {
            currentState.copy(
                mobsActiveStatus = currentState.mobsActiveStatus + (mob.phaseMobId to (currentActiveStatus + newStatus)),
            )
        } else {
            currentState
        }
    }

    fun applyMobsBuff(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        charInfo: BattleCharInfo,
        mobsInfo: Map<String, BattleMobInfo>,
    ): HistoryModeBattleInternalState {
        val result = applyMobsBuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        val currentMap = currentState.mobsActiveStatus
        val newMap = currentMap.toMutableMap()

        mobsInfo.keys.forEach { phaseMobId ->
            val buffs = result.buffs[phaseMobId] ?: emptyList()
            val currentStatus = currentMap[phaseMobId] ?: emptyList()

            val newBuffs = buffs.map { buff ->
                val mob = uiState.mobs.find { it.phaseMobId == phaseMobId }!!
                val skill = mob.skills.find { it.id == buff.skillId } ?: return@map null

                battleMapper.mapToUIModel(
                    mobActiveStatus = buff,
                    skillName = skill.name,
                    skillDescription = skill.description,
                    skillImage = skill.image,
                )
            }.filterNotNull()

            newMap[phaseMobId] = currentStatus.filterNot { it is MobActiveStatusUIModel.BuffUIModel } + newBuffs
        }

        return currentState.copy(mobsActiveStatus = newMap)
    }

    fun applyMobsDoTDamage(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        charInfo: BattleCharInfo,
        mobsInfo: Map<String, BattleMobInfo>,
    ): HistoryModeBattleInternalState {
        val result = applyMobsDoTUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        val currentMap = currentState.mobsActiveStatus
        val newMap = currentMap.toMutableMap()

        mobsInfo.keys.forEach { phaseMobId ->
            val dots = result.dots[phaseMobId] ?: emptyList()
            val currentStatus = currentMap[phaseMobId] ?: emptyList()

            val newDots = dots.map { dot ->
                val char = uiState.char!!
                val skill = char.damageSkills.first { it.id == dot.skillId }

                battleMapper.mapToUIModel(
                    charActiveStatus = dot,
                    skillName = skill.name,
                    skillDescription = skill.description,
                    skillImage = skill.image,
                )
            }

            newMap[phaseMobId] = currentStatus.filterNot { it is CharActiveStatusUIModel.DoTUIModel } + newDots
        }

        var updatedState = currentState.copy(mobsActiveStatus = newMap)

        result.mobsHealth.forEach { (phaseMobId, newHealth) ->
            val mob = uiState.mobs.find { it.phaseMobId == phaseMobId } ?: return@forEach

            if (mob.actualHealth != newHealth) {
                updatedState = mobsHealthStateHandler.updateMobHealth(
                    currentState = updatedState,
                    mobs = uiState.mobs,
                    mobToUpdate = mob,
                    newEnemyHealth = newHealth,
                )
            }
        }

        return updatedState
    }

    fun applyMobsDebuff(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        charInfo: BattleCharInfo,
        mobsInfo: Map<String, BattleMobInfo>,
    ): HistoryModeBattleInternalState {
        val result = applyMobsDebuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        val currentMap = currentState.mobsActiveStatus
        val newMap = currentMap.toMutableMap()

        mobsInfo.keys.forEach { phaseMobId ->
            val debuffs = result.debuffs[phaseMobId] ?: emptyList()
            val currentStatus = currentMap[phaseMobId] ?: emptyList()

            val newDebuffs = debuffs.map { debuff ->
                val char = uiState.char!!
                val skill = char.debuffSkills.first { it.id == debuff.skillId }

                battleMapper.mapToUIModel(
                    charActiveStatus = debuff,
                    skillName = skill.name,
                    skillDescription = skill.description,
                    skillImage = skill.image,
                )
            }

            newMap[phaseMobId] = currentStatus.filterNot { it is CharActiveStatusUIModel.DebuffUIModel } + newDebuffs
        }

        return currentState.copy(mobsActiveStatus = newMap)
    }
}
