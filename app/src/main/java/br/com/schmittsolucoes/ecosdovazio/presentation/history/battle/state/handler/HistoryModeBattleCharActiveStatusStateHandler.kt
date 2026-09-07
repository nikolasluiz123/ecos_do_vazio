package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharBuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharDebuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharDoTUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleInfoMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleMapper
import javax.inject.Inject

class HistoryModeBattleCharActiveStatusStateHandler @Inject constructor(
    private val applyCharDoTUseCase: ApplyCharDoTUseCase,
    private val applyCharDebuffUseCase: ApplyCharDebuffUseCase,
    private val applyCharBuffUseCase: ApplyCharBuffUseCase,
    private val battleMapper: BattleMapper,
    private val battleInfoMapper: BattleInfoMapper,
    private val charHealthStateHandler: HistoryModeBattleCharHealthStateHandler,
) {

    fun registerCharBuff(
        currentState: HistoryModeBattleInternalState,
        skill: CharSkillUIModel,
        result: CharSkillUsageResult.Buff,
    ): HistoryModeBattleInternalState {
        val newStatus = CharActiveStatusUIModel.BuffUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedCharSkillInfo.Buff,
            skillImage = skill.image,
            skillCategory = skill.skillCategory
        )

        return registerCharActiveStatus(currentState, newStatus)
    }

    fun registerCharDot(
        currentState: HistoryModeBattleInternalState,
        mob: BattleMobUIModel,
        skill: MobSkillUIModel,
        result: MobSkillUsageResult.DamageOverTime,
    ): HistoryModeBattleInternalState {
        val newDot = MobActiveStatusUIModel.DoTUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedMobSkillInfo.DamageOverTime,
            skillImage = skill.image,
            sourceId = mob.phaseMobId
        )

        return registerCharActiveStatus(currentState, newDot)
    }

    fun registerCharDebuff(
        currentState: HistoryModeBattleInternalState,
        mob: BattleMobUIModel,
        skill: MobSkillUIModel,
        result: MobSkillUsageResult.Debuff,
    ): HistoryModeBattleInternalState {
        val newDot = MobActiveStatusUIModel.DebuffUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedMobSkillInfo.Debuff,
            skillImage = skill.image,
            sourceId = mob.phaseMobId,
            skillCategory = skill.skillCategory
        )

        return registerCharActiveStatus(currentState, newDot)
    }

    fun registerCharActiveStatus(
        currentState: HistoryModeBattleInternalState,
        newStatus: ActiveStatusUIModel,
    ): HistoryModeBattleInternalState {
        return if (currentState.charActiveStatus.none { it.skillId == newStatus.skillId }) {
            currentState.copy(charActiveStatus = currentState.charActiveStatus + newStatus)
        } else {
            currentState
        }
    }

    fun applyCharBuff(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        charInfo: BattleCharInfo,
        mobsInfo: Map<String, BattleMobInfo>,
    ): HistoryModeBattleInternalState {
        val result = applyCharBuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        val currentList = currentState.charActiveStatus
        val newBuffs = result.buffs.map { buff ->
            val char = uiState.char!!
            val skill = char.buffSkills.find { it.id == buff.skillId } ?: return@map null

            battleMapper.mapToUIModel(
                charActiveStatus = buff,
                skillName = skill.name,
                skillDescription = skill.description,
                skillImage = skill.image
            )
        }.filterNotNull()

        return currentState.copy(
            charActiveStatus = currentList.filterNot { it is CharActiveStatusUIModel.BuffUIModel } + newBuffs
        )
    }

    fun applyCharDoTDamage(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        charInfo: BattleCharInfo,
        mobsInfo: Map<String, BattleMobInfo>,
    ): HistoryModeBattleInternalState {
        val result = applyCharDoTUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        val currentList = currentState.charActiveStatus
        val newDots = result.dots.map { dot ->
            val mob = uiState.mobs.find { it.phaseMobId == dot.sourceId }!!
            val skill = mob.skills.first { it.id == dot.skillId }

            battleMapper.mapToUIModel(
                mobActiveStatus = dot,
                skillName = skill.name,
                skillDescription = skill.description,
                skillImage = skill.image
            )
        }

        var stateWithDots = currentState.copy(
            charActiveStatus = currentList.filterNot { it is MobActiveStatusUIModel.DoTUIModel } + newDots
        )

        if (charInfo.actualHealth != result.charHealth) {
            stateWithDots = charHealthStateHandler.updateCharHealth(stateWithDots, result.charHealth)
        }

        return stateWithDots
    }

    fun applyCharDebuff(
        currentState: HistoryModeBattleInternalState,
        uiState: HistoryModeBattleUIState,
        charInfo: BattleCharInfo,
        mobsInfo: Map<String, BattleMobInfo>,
    ): HistoryModeBattleInternalState {
        val result = applyCharDebuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        val currentList = currentState.charActiveStatus
        val newDebuffs = result.debuffs.map { debuff ->
            val mob = uiState.mobs.find { it.phaseMobId == debuff.sourceId }!!
            val skill = mob.skills.first { it.id == debuff.skillId }

            battleMapper.mapToUIModel(
                mobActiveStatus = debuff,
                skillName = skill.name,
                skillDescription = skill.description,
                skillImage = skill.image
            )
        }

        return currentState.copy(
            charActiveStatus = currentList.filterNot { it is MobActiveStatusUIModel.DebuffUIModel } + newDebuffs
        )
    }
}
