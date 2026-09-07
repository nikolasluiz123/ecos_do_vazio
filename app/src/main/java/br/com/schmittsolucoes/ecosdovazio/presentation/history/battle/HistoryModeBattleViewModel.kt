package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.manager.SnackbarManager
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.CalculateCharMultipliersUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.CalculateProjectedDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharBattleUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.CalculateMobMultipliersUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.MobsFromPhaseQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.RunEnemyRoundUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.EndHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.StartHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharBuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDamageSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDebuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.GetCharSkillBlockedUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.ActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation.HistoryModeBattleRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleInternalState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.HistoryModeBattleUIState
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler.CharSkillUsageExecutionResult
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler.HistoryModeBattleActiveStatusStateHandler
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler.HistoryModeBattleCharSkillUsageStateHandler
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.state.handler.HistoryModeBattleMobSkillUsageStateHandler
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleInfoMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.BattleMapper
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.SkillMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HistoryModeBattleViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val battleMapper: BattleMapper,
    private val calculateCharMultipliersUseCase: CalculateCharMultipliersUseCase,
    private val calculateMobMultipliersUseCase: CalculateMobMultipliersUseCase,
    private val calculateProjectedDamageUseCase: CalculateProjectedDamageUseCase,
    private val getCharSkillBlockedUseCase: GetCharSkillBlockedUseCase,
    private val runEnemyRoundUseCase: RunEnemyRoundUseCase,
    private val startHistoryPhaseUseCase: StartHistoryPhaseUseCase,
    private val endHistoryPhaseUseCase: EndHistoryPhaseUseCase,
    private val snackbarManager: SnackbarManager,
    private val skillMapper: SkillMapper,
    private val battleInfoMapper: BattleInfoMapper,
    private val activeStatusStateHandler: HistoryModeBattleActiveStatusStateHandler,
    private val charSkillUsageStateHandler: HistoryModeBattleCharSkillUsageStateHandler,
    private val mobSkillUsageStateHandler: HistoryModeBattleMobSkillUsageStateHandler,
    savedStateHandle: SavedStateHandle,
    mobsFromPhaseQueryUseCase: MobsFromPhaseQueryUseCase,
    getCharBattleUseCase: GetCharBattleUseCase,
    charDamageSkillsQueryUseCase: CharDamageSkillsQueryUseCase,
    charBuffSkillsQueryUseCase: CharBuffSkillsQueryUseCase,
    charDebuffSkillsQueryUseCase: CharDebuffSkillsQueryUseCase,
) : CommonViewModel() {

    private val route = savedStateHandle.toRoute<HistoryModeBattleRoute>()

    private val _internalState = MutableStateFlow(HistoryModeBattleInternalState())

    private var isPhaseStarted = false

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<HistoryModeBattleUIState> = combine(
        mobsFromPhaseQueryUseCase(route.phaseId),
        getCharBattleUseCase(),
        charDamageSkillsQueryUseCase(),
        charBuffSkillsQueryUseCase(),
        charDebuffSkillsQueryUseCase(),
        _internalState,
    ) { flows ->
        val mobs = flows[0] as List<BattleMob>
        val char = flows[1] as BattleChar
        val damageSkills = flows[2] as List<CharSkill>
        val buffSkills = flows[3] as List<CharSkill>
        val debuffSkills = flows[4] as List<CharSkill>
        val internalState = flows[5] as HistoryModeBattleInternalState

        val uiModelMobs = mapBattleMobsToUIModel(mobs, internalState.mobsHealth, internalState.mobsActiveStatus, internalState.skillsRefreshTime)
        val selectedMob = uiModelMobs.find { it.phaseMobId == internalState.selectedMobId } ?: uiModelMobs.firstOrNull()
        val mobInfo = selectedMob?.let { battleInfoMapper.mapToDomainInfo(it) }

        val actualCharInfo = battleInfoMapper.mapToDomainInfo(
            mapBattleCharToUIModel(char, internalState.charHealth, internalState.charActiveStatus),
        )

        val uiModelChar = mapBattleCharToUIModel(
            char = char,
            charHealth = internalState.charHealth,
            charActiveStatus = internalState.charActiveStatus,
            damageSkills = mapCharSkillsToUIModel(char, actualCharInfo, damageSkills, internalState.skillsRefreshTime, mobInfo),
            buffSkills = mapCharSkillsToUIModel(char, actualCharInfo, buffSkills, internalState.skillsRefreshTime, mobInfo),
            debuffSkills = mapCharSkillsToUIModel(char, actualCharInfo, debuffSkills, internalState.skillsRefreshTime, mobInfo),
        )

        HistoryModeBattleUIState(
            phaseId = route.phaseId,
            errorMessage = internalState.errorMessage,
            shouldPop = internalState.shouldPop,
            mobs = uiModelMobs,
            char = uiModelChar,
            selectedMob = selectedMob,
            selectedSkill = internalState.selectedSkill,
            selectedActiveStatus = internalState.selectedDot,
            actualRound = internalState.actualRound,
            isEnemyRound = isEnemyRound(internalState.actualRound),
        )
    }.stateInWithCommonError(
        initialValue = HistoryModeBattleUIState(
            phaseId = route.phaseId,
        )
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
            is UserException.UserNotFound -> context.getString(R.string.user_error_not_found)
            else -> context.getString(R.string.error_unexpected)
        }
    }

    override fun onShowErrorDialog(message: String) {
        _internalState.update { it.copy(errorMessage = message) }
    }

    fun onDismissErrorDialog() {
        _internalState.update { it.copy(errorMessage = null) }
    }

    fun onMobClick(mob: BattleMobUIModel) {
        _internalState.update { it.copy(selectedMobId = mob.phaseMobId) }
    }

    fun onSkillLongClick(skill: CharSkillUIModel) {
        _internalState.update { it.copy(selectedSkill = skill) }
    }

    fun onDismissSkillTooltip() {
        _internalState.update { it.copy(selectedSkill = null) }
    }

    fun onStatusClick(dot: ActiveStatusUIModel) {
        _internalState.update { it.copy(selectedDot = dot) }
    }

    fun onDismissDotTooltip() {
        _internalState.update { it.copy(selectedDot = null) }
    }

    fun onSkillClick(skill: CharSkillUIModel) {
        val result = charSkillUsageStateHandler.executeSkillUsage(
            currentState = _internalState.value,
            uiState = uiState.value,
            skill = skill
        )

        when (result) {
            is CharSkillUsageExecutionResult.Executed -> {
                _internalState.value = result.newState
            }

            is CharSkillUsageExecutionResult.NoSelectedMob -> {
                val message = context.getString(R.string.history_mode_battle_screen_select_mob_message)
                snackbarManager.showSnackbar(message)
            }

            is CharSkillUsageExecutionResult.Ignored -> { }
        }
    }

    private fun decrementSkillsRefreshTime() {
        _internalState.update { state ->
            val updatedMap = state.skillsRefreshTime.mapValues { (_, time) ->
                if (time > 0) time - 1 else 0
            }.filterValues { it > 0 }

            state.copy(skillsRefreshTime = updatedMap)
        }
    }

    fun onRoundUpdate() {
        launch {
            if (!isPhaseStarted) {
                startHistoryPhaseUseCase(route.phaseId)
                isPhaseStarted = true
            }

            if (allMobsIsDead() || charIsDead()) {
                tryFinishBattle()
                return@launch
            }

            _internalState.update { currentState ->
                activeStatusStateHandler.applyAllTurnStatuses(
                    currentState = currentState,
                    uiState = uiState.value,
                    battleInfoMapper = battleInfoMapper
                )
            }

            if (isEnemyRound()) {
                runEnemyRoundUseCase(
                    getCharInfo = { battleInfoMapper.mapToDomainInfo(uiState.value.char!!) },
                    mobs = uiState.value.mobs.map { battleMapper.mapToDomain(it) },
                    onMobUseSkill = ::handleMobSkillResult
                )

                if (!allMobsIsDead()) {
                    incrementRound()
                }
            } else {
                decrementSkillsRefreshTime()
            }

            tryFinishBattle()
        }
    }

    private suspend fun tryFinishBattle() {
        if (_internalState.value.shouldPop) return

        val char = uiState.value.char ?: return

        val result = endHistoryPhaseUseCase(
            phaseId = route.phaseId,
            battleCharInfo = battleInfoMapper.mapToDomainInfo(char),
            mobs = uiState.value.mobs.map { battleInfoMapper.mapToDomainInfo(it) }
        )

        if (result.isHistoryFinished) {
            if (allMobsIsDead()) {
                val message = if (result.levelInfo.levelUp) {
                    context.getString(
                        R.string.history_mode_battle_victory_level_up,
                        result.levelInfo.currentLevel
                    )
                } else {
                    context.getString(R.string.history_mode_battle_victory)
                }

                snackbarManager.showSnackbar(message)
            }

            _internalState.update { it.copy(shouldPop = true) }
        }
    }

    private fun isEnemyRound(actualRound: Long = uiState.value.actualRound): Boolean {
        return actualRound % 2 == 0L
    }

    private fun handleMobSkillResult(result: MobSkillUsageResult) {
        _internalState.update { currentState ->
            mobSkillUsageStateHandler.handleMobSkillResult(
                currentState = currentState,
                uiState = uiState.value,
                result = result
            )
        }
    }

    private fun mapBattleMobsToUIModel(
        mobs: List<BattleMob>,
        mobsHealth: Map<String, Long>,
        mobsActiveStatus: Map<String, List<ActiveStatusUIModel>>,
        skillsRefreshTime: Map<String, Int> = emptyMap()
    ): List<BattleMobUIModel> {
        return mobs.map { battleMob ->
            val actualHealth = mobsHealth[battleMob.phaseMobId] ?: battleMob.actualHealth
            val activeStatus = mobsActiveStatus[battleMob.phaseMobId] ?: emptyList()
            val tempUIModel = battleMapper.mapToUIModel(
                battleMob = battleMob.copy(actualHealth = actualHealth),
                skills = battleMob.skills.map { skill ->
                    skillMapper.mapToUIModel(
                        skill = skill,
                        currentRefreshTime = skillsRefreshTime[skill.id] ?: skill.currentRefreshTime
                    )
                },
                activeStatus = activeStatus
            )

            val multipliers = calculateMobMultipliersUseCase(
                battleInfoMapper.mapToDomainInfo(tempUIModel)
            )

            tempUIModel.copy(
                offensiveMultiplier = multipliers.offensive,
                defensiveMultiplier = multipliers.defensive
            )
        }
    }

    private fun mapBattleCharToUIModel(
        char: BattleChar,
        charHealth: Long?,
        charActiveStatus: List<ActiveStatusUIModel> = emptyList(),
        damageSkills: List<CharSkillUIModel> = emptyList(),
        buffSkills: List<CharSkillUIModel> = emptyList(),
        debuffSkills: List<CharSkillUIModel> = emptyList()
    ): BattleCharUIModel {
        val actualHealth = charHealth ?: char.actualHealth

        val tempUIModel = battleMapper.mapToUIModel(
            char = char.copy(actualHealth = actualHealth),
            offensiveMultiplier = 1.0,
            defensiveMultiplier = 0.0,
            damageSkills = damageSkills,
            buffSkills = buffSkills,
            debuffSkills = debuffSkills,
            activeStatus = charActiveStatus
        )

        val multipliers = calculateCharMultipliersUseCase(
            battleInfoMapper.mapToDomainInfo(tempUIModel)
        )

        return tempUIModel.copy(
            offensiveMultiplier = multipliers.offensive,
            defensiveMultiplier = multipliers.defensive
        )
    }

    private fun mapCharSkillsToUIModel(
        battleChar: BattleChar,
        charInfo: BattleCharInfo,
        skills: List<CharSkill>,
        skillsRefreshTime: Map<String, Int>,
        mobInfo: BattleMobInfo? = null
    ): List<CharSkillUIModel> {
        return skills.map { skill ->
            val projectedDamageInfo = calculateProjectedDamageUseCase(
                skill = skill,
                charInfo = charInfo,
                mobInfo = mobInfo
            )

            skillMapper.mapToUIModel(
                skill = skill,
                currentRefreshTime = skillsRefreshTime[skill.id] ?: 0,
                blocked = getCharSkillBlockedUseCase(
                    battleChar = battleChar,
                    skillRequiredAttributes = skill.attributes,
                    minLevel = skill.minLevel
                ),
                projectedDamageInfo = projectedDamageInfo
            )
        }
    }

    private fun incrementRound() {
        _internalState.update { it.copy(actualRound = it.actualRound + 1) }
    }

    private fun charIsDead(): Boolean {
        val state = _internalState.value
        val notLoaded = state.charHealth == null && uiState.value.char?.actualHealth == null
        if (notLoaded) return false

        return (state.charHealth ?: uiState.value.char?.actualHealth ?: 0) <= 0
    }

    private fun allMobsIsDead(): Boolean {
        val state = _internalState.value
        val notLoaded = state.mobsHealth.isEmpty() && uiState.value.mobs.all { it.actualHealth <= 0 }
        if (notLoaded) return false

        val mobsHealth = state.mobsHealth.ifEmpty {
            uiState.value.mobs.associate { it.phaseMobId to it.actualHealth }
        }

        return mobsHealth.all { it.value <= 0 }
    }
}
