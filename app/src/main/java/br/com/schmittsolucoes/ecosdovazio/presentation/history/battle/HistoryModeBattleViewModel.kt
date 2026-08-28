package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
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
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharDebuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyCharDoTUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsDebuffUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.ApplyMobsDoTUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharBattleUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.UseCharSkillUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.MobsFromPhaseQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.RunEnemyRoundUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.EndHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.StartHistoryPhaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharBuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDamageSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDebuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.GetCharSkillBlockedUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.collections.map

@HiltViewModel
class HistoryModeBattleViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val battleMapper: BattleMapper,
    private val getMobHPUseCase: GetMobHPUseCase,
    private val getCharHPUseCase: GetCharHPUseCase,
    private val applyMobsDoTUseCase: ApplyMobsDoTUseCase,
    private val applyCharDoTUseCase: ApplyCharDoTUseCase,
    private val applyMobsDebuffUseCase: ApplyMobsDebuffUseCase,
    private val applyCharDebuffUseCase: ApplyCharDebuffUseCase,
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
    charDebuffSkillsQueryUseCase: CharDebuffSkillsQueryUseCase
) : CommonViewModel() {

    private val route = savedStateHandle.toRoute<HistoryModeBattleRoute>()

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)
    private val _selectedMobId = MutableStateFlow<String?>(null)
    private val _selectedSkill = MutableStateFlow<CharSkillUIModel?>(null)
    private val _charHealth = MutableStateFlow<Long?>(null)
    private val _mobsHealth = MutableStateFlow<Map<String, Long>>(emptyMap())
    private val _mobsActiveStatus = MutableStateFlow<Map<String, List<CharActiveStatusUIModel>>>(emptyMap())
    private val _charActiveStatus = MutableStateFlow<List<MobActiveStatusUIModel>>(emptyList())
    private val _skillsRefreshTime = MutableStateFlow<Map<String, Int>>(emptyMap())
    private val _actualRound = MutableStateFlow<Long>(1)
    private val _shouldPop = MutableStateFlow(false)
    private val _selectedDot = MutableStateFlow<ActiveStatusUIModel?>(null)

    private var isPhaseStarted = false

    @Suppress("UNCHECKED_CAST")
    val uiState: StateFlow<HistoryModeBattleUIState> = combine(
        _errorMessage,
        _isLoading,
        mobsFromPhaseQueryUseCase(route.phaseId),
        getCharBattleUseCase(),
        charDamageSkillsQueryUseCase(),
        charBuffSkillsQueryUseCase(),
        charDebuffSkillsQueryUseCase(),
        _selectedMobId,
        _selectedSkill,
        _mobsHealth,
        _mobsActiveStatus,
        _charActiveStatus,
        _skillsRefreshTime,
        _charHealth,
        _actualRound,
        _shouldPop,
        _selectedDot
    ) { flows ->
        val errorMessage = flows[0] as String?
        val isLoading = flows[1] as Boolean
        val mobs = flows[2] as List<BattleMob>
        val char = flows[3] as BattleChar
        val damageSkills = flows[4] as List<CharSkill>
        val buffSkills = flows[5] as List<CharSkill>
        val debuffSkills = flows[6] as List<CharSkill>
        val selectedMobId = flows[7] as String?
        val selectedSkill = flows[8] as CharSkillUIModel?
        val mobsHealth = flows[9] as Map<String, Long>
        val mobsActiveStatus = flows[10] as Map<String, List<CharActiveStatusUIModel>>
        val charActiveStatus = flows[11] as List<MobActiveStatusUIModel>
        val skillsRefreshTime = flows[12] as Map<String, Int>
        val charHealth = flows[13] as Long?
        val actualRound = flows[14] as Long
        val shouldPop = flows[15] as Boolean
        val selectedDot = flows[16] as ActiveStatusUIModel?

        val uiModelMobs = mapBattleMobsToUIModel(mobs, mobsHealth, mobsActiveStatus)
        val uiModelChar = mapBattleCharToUIModel(
            char = char,
            charHealth = charHealth,
            charActiveStatus = charActiveStatus,
            damageSkills = mapCharSkillsToUIModel(char, damageSkills, skillsRefreshTime),
            buffSkills = mapCharSkillsToUIModel(char, buffSkills, skillsRefreshTime),
            debuffSkills = mapCharSkillsToUIModel(char, debuffSkills, skillsRefreshTime)
        )

        val selectedMob = uiModelMobs.find { it.phaseMobId == selectedMobId } ?: uiModelMobs.firstOrNull()

        HistoryModeBattleUIState(
            phaseId = route.phaseId,
            errorMessage = errorMessage,
            shouldPop = shouldPop,
            isLoading = isLoading,
            mobs = uiModelMobs,
            char = uiModelChar,
            selectedMob = selectedMob,
            selectedSkill = selectedSkill,
            selectedDot = selectedDot,
            actualRound = actualRound,
            isEnemyRound = isEnemyRound(actualRound)
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STATE_IN_STOP_TIMEOUT_MILLIS),
        initialValue = HistoryModeBattleUIState(
            phaseId = route.phaseId,
            isLoading = true
        )
    )

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return when (throwable) {
            is UserException.UserNotFound -> context.getString(R.string.user_error_not_found)
            else -> context.getString(R.string.error_unexpected)
        }
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onMobClick(mob: BattleMobUIModel) {
        _selectedMobId.value = mob.phaseMobId
    }

    fun onSkillLongClick(skill: CharSkillUIModel) {
        _selectedSkill.value = skill
    }

    fun onDismissSkillTooltip() {
        _selectedSkill.value = null
    }

    fun onStatusClick(dot: ActiveStatusUIModel) {
        _selectedDot.value = dot
    }

    fun onDismissDotTooltip() {
        _selectedDot.value = null
    }

    fun onSkillClick(skill: CharSkillUIModel) {
        val state = uiState.value

        if (skill.currentRefreshTime > 0 || skill.blocked) return

        if (state.selectedMob != null) {
            val char = state.char ?: return

            val result = useCharSkillUseCase(
                skillInfo = battleInfoMapper.mapToUsedSkillInfo(skill),
                battleCharInfo = battleInfoMapper.mapToDomainInfo(char),
                battleMobInfo = battleInfoMapper.mapToDomainInfo(state.selectedMob)
            )

            when (result) {
                is CharSkillUsageResult.CommonDamage -> {
                    updateMobHealth(state.selectedMob, result.newEnemyHealth)
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
            selectedMob = selectedMob,
            skill = skill,
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
            selectedMob = selectedMob,
            skill = skill,
            newStatus = newStatus
        )
    }

    private fun registerMobActiveStatus(
        selectedMob: BattleMobUIModel,
        skill: CharSkillUIModel,
        newStatus: CharActiveStatusUIModel
    ) {
        val currentActiveStatus = _mobsActiveStatus.value[selectedMob.phaseMobId] ?: emptyList()

        if (currentActiveStatus.none { it.skillId == skill.id }) {
            _mobsActiveStatus.value = _mobsActiveStatus.value.toMutableMap().apply {
                put(selectedMob.phaseMobId, currentActiveStatus + newStatus)
            }
        }
    }

    private fun registerCharActiveStatus(skillId: String, newStatus: MobActiveStatusUIModel) {
        if (_charActiveStatus.value.none { it.skillId == skillId }) {
            _charActiveStatus.value += newStatus
        }
    }

    private fun updateSkillRefreshTime(skillId: String, refreshTime: Int) {
        _skillsRefreshTime.value = _skillsRefreshTime.value.toMutableMap().apply {
            put(skillId, refreshTime)
        }
    }

    private fun decrementSkillsRefreshTime() {
        _skillsRefreshTime.value = _skillsRefreshTime.value.mapValues { (_, time) ->
            if (time > 0) time - 1 else 0
        }.filterValues { it > 0 }
    }

    private fun updateMobHealth(selectedMob: BattleMobUIModel, newEnemyHealth: Long) {
        var currentHealths = _mobsHealth.value

        if (currentHealths.isEmpty()) {
            currentHealths = uiState.value.mobs.associate { it.phaseMobId to it.actualHealth }
        }

        val updatedHealths = currentHealths.toMutableMap().apply {
            put(selectedMob.phaseMobId, newEnemyHealth)
        }

        _mobsHealth.value = updatedHealths

        val currentSelectedId = _selectedMobId.value ?: uiState.value.mobs.firstOrNull()?.phaseMobId

        if (newEnemyHealth <= 0 && selectedMob.phaseMobId == currentSelectedId) {
            val nextMob = uiState.value.mobs.firstOrNull { mob ->
                val health = updatedHealths[mob.phaseMobId] ?: 0L
                health > 0
            }

            if (nextMob != null) {
                _selectedMobId.value = nextMob.phaseMobId
            }
        }
    }

    private fun updateCharHealth(newHealth: Long) {
        _charHealth.value = newHealth
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
        if (_shouldPop.value) return

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

            _shouldPop.value = true
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

    private fun applyMobsDoTDamage(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyMobsDoTUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _mobsActiveStatus.update { currentMap ->
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

            newMap
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

        _mobsActiveStatus.update { currentMap ->
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

            newMap
        }
    }

    private fun applyCharDoTDamage(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyCharDoTUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _charActiveStatus.update { currentList ->
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

            currentList.filterNot { it is MobActiveStatusUIModel.DoTUIModel } + newDots
        }

        if (charInfo.actualHealth != result.charHealth) {
            updateCharHealth(result.charHealth)
        }
    }

    private fun applyCharDebuff(charInfo: BattleCharInfo, mobsInfo: Map<String, BattleMobInfo>) {
        val result = applyCharDebuffUseCase(battleCharInfo = charInfo, mobs = mobsInfo)

        _charActiveStatus.update { currentList ->
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

            currentList.filterNot { it is MobActiveStatusUIModel.DebuffUIModel } + newDebuffs
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
        }
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

        registerCharActiveStatus(skill.id, newDot)
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

        registerCharActiveStatus(skill.id, newDot)
    }

    private fun getMobSkill(mob: BattleMobUIModel, skillId: String): MobSkillUIModel? {
        return mob.skills.firstOrNull { it.id == skillId }
    }

    private fun getSelectedMobOrFirst(state: HistoryModeBattleUIState): BattleMobUIModel? {
        return state.mobs.firstOrNull { it.phaseMobId == _selectedMobId.value } ?: state.mobs.firstOrNull()
    }

    private fun mapBattleMobsToUIModel(
        mobs: List<BattleMob>,
        mobsHealth: Map<String, Long>,
        mobsActiveStatus: Map<String, List<CharActiveStatusUIModel>>
    ): List<BattleMobUIModel> {
        return mobs.map { battleMob ->
            val totalHealth = getMobHPUseCase(battleMob.mobCategory, battleMob.attributes.vitality)
            val actualHealth = mobsHealth[battleMob.phaseMobId] ?: totalHealth

            battleMapper.mapToUIModel(
                battleMob = battleMob.copy(actualHealth = actualHealth),
                totalHealth = totalHealth,
                healthProgress = if (totalHealth > 0) actualHealth.toFloat() / totalHealth.toFloat() else 0f,
                skills = battleMob.skills.map { skillMapper.mapToUIModel(it) },
                activeStatus = mobsActiveStatus[battleMob.phaseMobId] ?: emptyList()
            )
        }
    }

    private fun mapBattleCharToUIModel(
        char: BattleChar,
        charHealth: Long?,
        charActiveStatus: List<MobActiveStatusUIModel> = emptyList(),
        damageSkills: List<CharSkillUIModel> = emptyList(),
        buffSkills: List<CharSkillUIModel> = emptyList(),
        debuffSkills: List<CharSkillUIModel> = emptyList()
    ): BattleCharUIModel {
        val totalHealth = getCharHPUseCase.calculate(
            classCategory = char.classCategory,
            vitalityPoints = char.vitality.totalValue
        )

        val actualHealth = charHealth ?: totalHealth

        return battleMapper.mapToUIModel(
            char = char,
            totalHealth = totalHealth,
            actualHealth = actualHealth,
            healthProgress = if (totalHealth > 0) actualHealth.toFloat() / totalHealth.toFloat() else 0f,
            offensiveMultiplier = 1.0,
            defensiveMultiplier = 0.0,
            damageSkills = damageSkills,
            buffSkills = buffSkills,
            debuffSkills = debuffSkills,
            activeStatus = charActiveStatus
        )
    }

    private fun mapCharSkillsToUIModel(
        battleChar: BattleChar,
        skills: List<CharSkill>,
        skillsRefreshTime: Map<String, Int>
    ): List<CharSkillUIModel> {
        return skills.map {
            skillMapper.mapToUIModel(
                skill = it,
                currentRefreshTime = skillsRefreshTime[it.id] ?: 0,
                blocked = getCharSkillBlockedUseCase(
                    battleChar = battleChar,
                    skillRequiredAttributes = it.attributes,
                    minLevel = it.minLevel
                )
            )
        }
    }

    private fun incrementRound() {
        _actualRound.value += 1
    }

    private fun charIsDead(): Boolean {
        val notLoaded = _charHealth.value == null && uiState.value.char?.actualHealth == null
        if (notLoaded) return false

        return (_charHealth.value ?: uiState.value.char?.actualHealth ?: 0) <= 0
    }

    private fun allMobsIsDead(): Boolean {
        val notLoaded = _mobsHealth.value.isEmpty() && uiState.value.mobs.all { it.actualHealth <= 0 }
        if (notLoaded) return false

        val mobsHealth = _mobsHealth.value.ifEmpty {
            uiState.value.mobs.associate { it.phaseMobId to it.actualHealth }
        }

        return mobsHealth.all { it.value <= 0 }
    }
}
