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
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedCharSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.UsedMobSkillInfo
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharBuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharDebuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharDoTUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsBuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsDebuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsDoTUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.CalculateCharMultipliersUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.CalculateProjectedDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharBattleUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.UseCharSkillUseCase
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
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobActiveStatusUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation.HistoryModeBattleRoute
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

private data class HistoryModeBattleInternalState(
    val errorMessage: String? = null,
    val selectedMobId: String? = null,
    val selectedSkill: CharSkillUIModel? = null,
    val charHealth: Long? = null,
    val mobsHealth: Map<String, Long> = emptyMap(),
    val mobsActiveStatus: Map<String, List<ActiveStatusUIModel>> = emptyMap(),
    val charActiveStatus: List<ActiveStatusUIModel> = emptyList(),
    val skillsRefreshTime: Map<String, Int> = emptyMap(),
    val actualRound: Long = 1,
    val shouldPop: Boolean = false,
    val selectedDot: ActiveStatusUIModel? = null,
)

@HiltViewModel
class HistoryModeBattleViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val battleMapper: BattleMapper,
    private val applyMobsDoTUseCase: ApplyMobsDoTUseCase,
    private val applyCharDoTUseCase: ApplyCharDoTUseCase,
    private val applyMobsDebuffUseCase: ApplyMobsDebuffUseCase,
    private val applyCharDebuffUseCase: ApplyCharDebuffUseCase,
    private val applyMobsBuffUseCase: ApplyMobsBuffUseCase,
    private val applyCharBuffUseCase: ApplyCharBuffUseCase,
    private val calculateCharMultipliersUseCase: CalculateCharMultipliersUseCase,
    private val calculateMobMultipliersUseCase: CalculateMobMultipliersUseCase,
    private val calculateProjectedDamageUseCase: CalculateProjectedDamageUseCase,
    private val getCharSkillBlockedUseCase: GetCharSkillBlockedUseCase,
    private val useCharSkillUseCase: UseCharSkillUseCase,
    private val runEnemyRoundUseCase: RunEnemyRoundUseCase,
    private val startHistoryPhaseUseCase: StartHistoryPhaseUseCase,
    private val endHistoryPhaseUseCase: EndHistoryPhaseUseCase,
    private val snackbarManager: SnackbarManager,
    private val skillMapper: SkillMapper,
    private val battleInfoMapper: BattleInfoMapper,
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
        val state = uiState.value

        if (skill.currentRefreshTime > 0 || skill.blocked) return

        if (state.selectedMob != null) {
            val char = state.char ?: return

            val result = useCharSkillUseCase(
                skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill),
                battleCharInfo = battleInfoMapper.mapToDomainInfo(char),
                mobs = state.mobs.map { battleInfoMapper.mapToDomainInfo(it) },
                selectedMobId = state.selectedMob.phaseMobId
            )

            when (result) {
                is CharSkillUsageResult.CommonDamage -> {
                    updateMobHealth(state.selectedMob, result.newEnemyHealth)
                    incrementRound()
                }

                is CharSkillUsageResult.AreaDamage -> {
                    result.newEnemyHealths.forEach { (phaseMobId, newHealth) ->
                        val mob = getMobById(phaseMobId) ?: return@forEach
                        updateMobHealth(mob, newHealth)
                    }

                    incrementRound()
                }

                is CharSkillUsageResult.DamageOverTime -> {
                    updateMobHealth(state.selectedMob, result.newEnemyHealth)
                    registerMobDot(state.selectedMob, skill, result)
                    incrementRound()
                }

                is CharSkillUsageResult.Debuff -> {
                    updateMobHealth(state.selectedMob, result.newEnemyHealth)
                    registerMobDebuff(state.selectedMob, skill, result)

                    if (allMobsIsDead()) {
                        incrementRound()
                    }
                }

                is CharSkillUsageResult.VampiricDamage -> {
                    updateMobHealth(state.selectedMob, result.newEnemyHealth)
                    updateCharHealth(result.newCharHealth)
                    incrementRound()
                }

                is CharSkillUsageResult.Buff -> {
                    registerCharBuff(skill, result)
                }
            }

            updateSkillRefreshTime(skill.id, result.refreshTime)
        } else {
            val message = context.getString(R.string.history_mode_battle_screen_select_mob_message)
            snackbarManager.showSnackbar(message)
        }
    }

    private fun registerMobDot(
        selectedMob: BattleMobUIModel,
        skill: CharSkillUIModel,
        result: CharSkillUsageResult.DamageOverTime
    ) {
        val newStatus = CharActiveStatusUIModel.DoTUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedCharSkillInfo.DamageOverTime,
            skillImage = skill.image
        )

        registerMobActiveStatus(
            mob = selectedMob,
            newStatus = newStatus
        )
    }

    private fun registerMobDebuff(
        selectedMob: BattleMobUIModel,
        skill: CharSkillUIModel,
        result: CharSkillUsageResult.Debuff
    ) {
        val newStatus = CharActiveStatusUIModel.DebuffUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedCharSkillInfo.Debuff,
            skillImage = skill.image,
            skillCategory = skill.skillCategory
        )

        registerMobActiveStatus(
            mob = selectedMob,
            newStatus = newStatus
        )
    }

    private fun registerCharBuff(skill: CharSkillUIModel, result: CharSkillUsageResult.Buff) {
        val newStatus = CharActiveStatusUIModel.BuffUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedCharSkillInfo.Buff,
            skillImage = skill.image,
            skillCategory = skill.skillCategory
        )

        registerCharActiveStatus(newStatus = newStatus)
    }

    private fun registerMobActiveStatus(mob: BattleMobUIModel, newStatus: ActiveStatusUIModel) {
        _internalState.update { state ->
            val currentActiveStatus = state.mobsActiveStatus[mob.phaseMobId] ?: emptyList()

            if (currentActiveStatus.none { it.skillId == newStatus.skillId }) {
                state.copy(
                    mobsActiveStatus = state.mobsActiveStatus + (mob.phaseMobId to (currentActiveStatus + newStatus))
                )
            } else {
                state
            }
        }
    }

    private fun registerCharActiveStatus(newStatus: ActiveStatusUIModel) {
        _internalState.update { state ->
            if (state.charActiveStatus.none { it.skillId == newStatus.skillId }) {
                state.copy(charActiveStatus = state.charActiveStatus + newStatus)
            } else {
                state
            }
        }
    }

    private fun updateSkillRefreshTime(skillId: String, refreshTime: Int) {
        _internalState.update { state ->
            state.copy(skillsRefreshTime = state.skillsRefreshTime + (skillId to refreshTime))
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

    private fun updateMobHealth(selectedMob: BattleMobUIModel, newEnemyHealth: Long) {
        val currentState = _internalState.value
        var currentHealths = currentState.mobsHealth

        if (currentHealths.isEmpty()) {
            currentHealths = uiState.value.mobs.associate { it.phaseMobId to it.actualHealth }
        }

        val updatedHealths = currentHealths.toMutableMap().apply {
            put(selectedMob.phaseMobId, newEnemyHealth)
        }

        val currentSelectedId = currentState.selectedMobId ?: uiState.value.mobs.firstOrNull()?.phaseMobId
        var newSelectedMobId = currentState.selectedMobId

        if (newEnemyHealth <= 0 && selectedMob.phaseMobId == currentSelectedId) {
            val nextMob = uiState.value.mobs.firstOrNull { mob ->
                val health = updatedHealths[mob.phaseMobId] ?: 0L
                health > 0
            }

            if (nextMob != null) {
                newSelectedMobId = nextMob.phaseMobId
            }
        }

        _internalState.update {
            it.copy(
                mobsHealth = updatedHealths,
                selectedMobId = newSelectedMobId
            )
        }
    }

    private fun updateCharHealth(newHealth: Long) {
        _internalState.update { it.copy(charHealth = newHealth) }
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

            applyDoTsDamage()
            applyDebuffs()
            applyBuffs()

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

    private fun applyDoTsDamage() {
        val char = uiState.value.char ?: return
        val charInfo = battleInfoMapper.mapToDomainInfo(char)
        val mobsInfo = uiState.value.mobs.associate { it.phaseMobId to battleInfoMapper.mapToDomainInfo(it) }

        applyMobsDoTDamage(charInfo, mobsInfo)
        applyCharDoTDamage(charInfo, mobsInfo)
    }

    private fun applyDebuffs() {
        val char = uiState.value.char ?: return
        val charInfo = battleInfoMapper.mapToDomainInfo(char)
        val mobsInfo = uiState.value.mobs.associate { it.phaseMobId to battleInfoMapper.mapToDomainInfo(it) }

        applyMobsDebuff(charInfo, mobsInfo)
        applyCharDebuff(charInfo, mobsInfo)
    }

    private fun applyBuffs() {
        val char = uiState.value.char ?: return
        val charInfo = battleInfoMapper.mapToDomainInfo(char)
        val mobsInfo = uiState.value.mobs.associate { it.phaseMobId to battleInfoMapper.mapToDomainInfo(it) }

        applyMobsBuff(charInfo, mobsInfo)
        applyCharBuff(charInfo, mobsInfo)
    }

    private fun applyMobsBuff(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyMobsBuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _internalState.update { state ->
            val currentMap = state.mobsActiveStatus
            val newMap = currentMap.toMutableMap()

            mobsInfo.keys.forEach { phaseMobId ->
                val buffs = result.buffs[phaseMobId] ?: emptyList()
                val currentStatus = currentMap[phaseMobId] ?: emptyList()

                val newBuffs = buffs.map { buff ->
                    val mob = uiState.value.mobs.find { it.phaseMobId == phaseMobId }!!
                    val skill = mob.skills.find { it.id == buff.skillId } ?: return@map null

                    battleMapper.mapToUIModel(
                        mobActiveStatus = buff,
                        skillName = skill.name,
                        skillDescription = skill.description,
                        skillImage = skill.image
                    )
                }.filterNotNull()

                newMap[phaseMobId] = currentStatus.filterNot { it is MobActiveStatusUIModel.BuffUIModel } + newBuffs
            }

            state.copy(mobsActiveStatus = newMap)
        }
    }

    private fun applyCharBuff(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyCharBuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _internalState.update { state ->
            val currentList = state.charActiveStatus
            val newBuffs = result.buffs.map { buff ->
                val char = uiState.value.char!!
                val skill = char.buffSkills.find { it.id == buff.skillId } ?: return@map null

                battleMapper.mapToUIModel(
                    charActiveStatus = buff,
                    skillName = skill.name,
                    skillDescription = skill.description,
                    skillImage = skill.image
                )
            }.filterNotNull()

            state.copy(charActiveStatus = currentList.filterNot { it is CharActiveStatusUIModel.BuffUIModel } + newBuffs)
        }
    }

    private fun applyMobsDoTDamage(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyMobsDoTUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _internalState.update { state ->
            val currentMap = state.mobsActiveStatus
            val newMap = currentMap.toMutableMap()

            mobsInfo.keys.forEach { phaseMobId ->
                val dots = result.dots[phaseMobId] ?: emptyList()
                val currentStatus = currentMap[phaseMobId] ?: emptyList()

                val newDots = dots.map { dot ->
                    val char = uiState.value.char!!
                    val skill = char.damageSkills.first { it.id == dot.skillId }

                    battleMapper.mapToUIModel(
                        charActiveStatus = dot,
                        skillName = skill.name,
                        skillDescription = skill.description,
                        skillImage = skill.image
                    )
                }

                newMap[phaseMobId] = currentStatus.filterNot { it is CharActiveStatusUIModel.DoTUIModel } + newDots
            }

            state.copy(mobsActiveStatus = newMap)
        }

        result.mobsHealth.forEach { (phaseMobId, newHealth) ->
            val mob = uiState.value.mobs.find { it.phaseMobId == phaseMobId } ?: return@forEach

            if (mob.actualHealth != newHealth) {
                updateMobHealth(mob, newHealth)
            }
        }
    }

    private fun applyMobsDebuff(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyMobsDebuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _internalState.update { state ->
            val currentMap = state.mobsActiveStatus
            val newMap = currentMap.toMutableMap()

            mobsInfo.keys.forEach { phaseMobId ->
                val debuffs = result.debuffs[phaseMobId] ?: emptyList()
                val currentStatus = currentMap[phaseMobId] ?: emptyList()

                val newDebuffs = debuffs.map { debuff ->
                    val char = uiState.value.char!!
                    val skill = char.debuffSkills.first { it.id == debuff.skillId }

                    battleMapper.mapToUIModel(
                        charActiveStatus = debuff,
                        skillName = skill.name,
                        skillDescription = skill.description,
                        skillImage = skill.image
                    )
                }

                newMap[phaseMobId] = currentStatus.filterNot { it is CharActiveStatusUIModel.DebuffUIModel } + newDebuffs
            }

            state.copy(mobsActiveStatus = newMap)
        }
    }

    private fun applyCharDoTDamage(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyCharDoTUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _internalState.update { state ->
            val currentList = state.charActiveStatus
            val newDots = result.dots.map { dot ->
                val mob = uiState.value.mobs.find { it.phaseMobId == dot.sourceId }!!
                val skill = mob.skills.first { it.id == dot.skillId }

                battleMapper.mapToUIModel(
                    mobActiveStatus = dot,
                    skillName = skill.name,
                    skillDescription = skill.description,
                    skillImage = skill.image
                )
            }

            state.copy(charActiveStatus = currentList.filterNot { it is MobActiveStatusUIModel.DoTUIModel } + newDots)
        }

        if (charInfo.actualHealth != result.charHealth) {
            updateCharHealth(result.charHealth)
        }
    }

    private fun applyCharDebuff(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyCharDebuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _internalState.update { state ->
            val currentList = state.charActiveStatus
            val newDebuffs = result.debuffs.map { debuff ->
                val mob = uiState.value.mobs.find { it.phaseMobId == debuff.sourceId }!!
                val skill = mob.skills.first { it.id == debuff.skillId }

                battleMapper.mapToUIModel(
                    mobActiveStatus = debuff,
                    skillName = skill.name,
                    skillDescription = skill.description,
                    skillImage = skill.image
                )
            }

            state.copy(charActiveStatus = currentList.filterNot { it is MobActiveStatusUIModel.DebuffUIModel } + newDebuffs)
        }
    }

    private fun handleMobSkillResult(result: MobSkillUsageResult) {
        when (result) {
            is MobSkillUsageResult.CommonDamage -> {
                updateCharHealth(result.newEnemyHealth)
            }

            is MobSkillUsageResult.DamageOverTime -> {
                updateCharHealth(result.newEnemyHealth)
                registerCharDot(result)
            }

            is MobSkillUsageResult.Debuff -> {
                updateCharHealth(result.newEnemyHealth)
                registerCharDebuff(result)
            }

            is MobSkillUsageResult.VampiricDamage -> {
                updateCharHealth(result.newEnemyHealth)
                getMobById(result.mobId)?.let { mob ->
                    updateMobHealth(mob, result.newCharHealth)
                }
            }

            is MobSkillUsageResult.Buff -> {
                registerMobBuff(result)
            }

            is MobSkillUsageResult.Heal -> {
                getMobById(result.targetMobId)?.let { mob ->
                    updateMobHealth(mob, result.newMobHealth)
                }
            }

            is MobSkillUsageResult.AreaHeal -> {
                result.newMobsHealth.forEach { (phaseMobId, newHealth) ->
                    getMobById(phaseMobId)?.let { mob ->
                        updateMobHealth(mob, newHealth)
                    }
                }
            }
        }

        updateSkillRefreshTime(result.skillId, result.refreshTime)
    }

    private fun registerMobBuff(result: MobSkillUsageResult.Buff) {
        val mob = getMobById(result.mobId) ?: return
        val skill = getMobSkill(mob, result.skillId) ?: return

        val newStatus = MobActiveStatusUIModel.BuffUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillImage = skill.image,
            sourceId = mob.phaseMobId,
            skillCategory = skill.skillCategory,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedMobSkillInfo.Buff
        )

        registerMobActiveStatus(mob, newStatus)
    }

    private fun registerCharDot(result: MobSkillUsageResult.DamageOverTime) {
        val state = uiState.value
        val mob = getSelectedMobOrFirst(state) ?: return
        val skill = getMobSkill(mob, result.skillId) ?: return

        val newDot = MobActiveStatusUIModel.DoTUIModel(
            skillId = skill.id,
            skillName = skill.name,
            skillDescription = skill.description,
            remainingTurns = result.repeat,
            skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill) as UsedMobSkillInfo.DamageOverTime,
            skillImage = skill.image,
            sourceId = mob.phaseMobId
        )

        registerCharActiveStatus(newDot)
    }

    private fun registerCharDebuff(result: MobSkillUsageResult.Debuff) {
        val state = uiState.value
        val mob = getSelectedMobOrFirst(state) ?: return
        val skill = getMobSkill(mob, result.skillId) ?: return

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

        registerCharActiveStatus(newDot)
    }

    private fun getMobSkill(mob: BattleMobUIModel, skillId: String): MobSkillUIModel? {
        return mob.skills.firstOrNull { it.id == skillId }
    }

    private fun getSelectedMobOrFirst(state: HistoryModeBattleUIState): BattleMobUIModel? {
        return state.mobs.firstOrNull { it.phaseMobId == _internalState.value.selectedMobId } ?: state.mobs.firstOrNull()
    }

    private fun getMobById(id: String): BattleMobUIModel? {
        return uiState.value.mobs.firstOrNull { it.phaseMobId == id }
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
