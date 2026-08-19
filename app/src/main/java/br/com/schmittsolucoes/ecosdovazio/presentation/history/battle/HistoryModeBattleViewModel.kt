package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.manager.SnackbarManager
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
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
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation.HistoryModeBattleRoute
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.toDomain
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.toDomainInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.toDomainUsedSkillInfo
import br.com.schmittsolucoes.ecosdovazio.presentation.mapper.toUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryModeBattleViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val resourcesProvider: ResourcesProvider,
    private val getMobHPUseCase: GetMobHPUseCase,
    private val getCharHPUseCase: GetCharHPUseCase,
    private val getCharSkillBlockedUseCase: GetCharSkillBlockedUseCase,
    private val useCharSkillUseCase: UseCharSkillUseCase,
    private val runEnemyRoundUseCase: RunEnemyRoundUseCase,
    private val startHistoryPhaseUseCase: StartHistoryPhaseUseCase,
    private val endHistoryPhaseUseCase: EndHistoryPhaseUseCase,
    private val snackbarManager: SnackbarManager,
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
    private val _actualRound = MutableStateFlow<Long>(1)
    private val _shouldPop = MutableStateFlow(false)

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
        _charHealth,
        _actualRound,
        _shouldPop
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
        val charHealth = flows[10] as Long?
        val actualRound = flows[11] as Long
        val shouldPop = flows[12] as Boolean

        val uiModelMobs = mapBattleMobsToUIModel(mobs, mobsHealth)
        val uiModelChar = mapBattleCharToUIModel(
            char = char,
            charHealth = charHealth,
            damageSkills = mapCharSkillsToUIModel(char, damageSkills),
            buffSkills = mapCharSkillsToUIModel(char, buffSkills),
            debuffSkills = mapCharSkillsToUIModel(char, debuffSkills)
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
            actualRound = actualRound
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

    fun onSkillClick(skill: CharSkillUIModel) {
        val state = uiState.value

        if (state.selectedMob != null) {
            val char = state.char ?: return

            val result = useCharSkillUseCase(
                skillInfo = skill.toDomainUsedSkillInfo(),
                battleCharInfo = char.toDomainInfo(),
                battleMobInfo = state.selectedMob.toDomainInfo()
            )

            when (result) {
                is CharSkillUsageResult.CommonDamage -> {
                    updateMobHealth(state.selectedMob, result.newEnemyHealth)
                }

                is CharSkillUsageResult.DamageOverTime -> {
                    updateMobHealth(state.selectedMob, result.newEnemyHealth)
                }
            }

            incrementRound()
        } else {
            val message = context.getString(R.string.history_mode_battle_screen_select_mob_message)
            snackbarManager.showSnackbar(message)
        }
    }

    private fun updateMobHealth(selectedMob: BattleMobUIModel, newEnemyHealth: Long) {
        _mobsHealth.value = _mobsHealth.value.toMutableMap().apply {
            put(selectedMob.phaseMobId, newEnemyHealth)
        }

        val mobToKill = uiState.value.mobs.firstOrNull { it.actualHealth > 0 }

        if (newEnemyHealth <= 0 && mobToKill != null) {
            _selectedMobId.value = mobToKill.phaseMobId
        }
    }

    fun onRoundUpdate() {
        launch {
            if (!isPhaseStarted) {
                startHistoryPhaseUseCase(route.phaseId)
                isPhaseStarted = true
            }

            if (isEnemyRound()) {
                val state = uiState.value
                val char = state.char ?: return@launch
                val mobs = state.mobs

                runEnemyRoundUseCase(
                    charInfo = char.toDomainInfo(),
                    mobs = mobs.map { it.toDomain() },
                    onMobUseSkill = ::handleMobSkillResult
                )
            }

            tryFinishBattle()
        }
    }

    private suspend fun tryFinishBattle() {
        val state = uiState.value
        val char = state.char ?: return
        val mobs = state.mobs
        val isFinished = endHistoryPhaseUseCase(
            phaseId = route.phaseId,
            battleCharInfo = char.toDomainInfo(),
            mobs = mobs.map { it.toDomainInfo() }
        )

        if (isFinished) {
            val allMobsDead = mobs.all { it.actualHealth <= 0 }

            if (allMobsDead) {
                snackbarManager.showSnackbar(context.getString(R.string.history_mode_battle_victory))
            }

            _shouldPop.value = true
        }
    }

    private fun isEnemyRound(): Boolean {
        return uiState.value.actualRound % 2 == 0L
    }

    private fun handleMobSkillResult(result: MobSkillUsageResult) {
        when (result) {
            is MobSkillUsageResult.CommonDamage -> {
                _charHealth.value = result.newEnemyHealth
            }
            is MobSkillUsageResult.DamageOverTime -> {
                _charHealth.value = result.newEnemyHealth
            }
        }
    }

    private fun mapBattleMobsToUIModel(
        mobs: List<BattleMob>,
        mobsHealth: Map<String, Long>
    ): List<BattleMobUIModel> {
        return mobs.map { battleMob ->
            val totalHealth = getMobHPUseCase(battleMob.mobCategory, battleMob.attributes.vitality)
            val actualHealth = mobsHealth[battleMob.phaseMobId] ?: totalHealth

            battleMob.copy(actualHealth = actualHealth).toUIModel(
                image = resourcesProvider.getBattleMobImage(battleMob.imageName) ?: 0,
                totalHealth = totalHealth,
                healthProgress = if (totalHealth > 0) actualHealth.toFloat() / totalHealth.toFloat() else 0f,
                skills = battleMob.skills.map { it.toUIModel() }
            )
        }
    }

    private fun mapBattleCharToUIModel(
        char: BattleChar,
        charHealth: Long?,
        damageSkills: List<CharSkillUIModel> = emptyList(),
        buffSkills: List<CharSkillUIModel> = emptyList(),
        debuffSkills: List<CharSkillUIModel> = emptyList()
    ): BattleCharUIModel {
        val totalHealth = getCharHPUseCase.calculate(
            classCategory = char.classCategory,
            vitalityPoints = char.vitality.totalValue
        )

        val battleImage = resourcesProvider.getBattleClassImage(char.battleImageName)
            ?: resourcesProvider.getBattleSpecializationImage(char.battleImageName)
            ?: 0

        val actualHealth = charHealth ?: totalHealth

        return char.toUIModel(
            totalHealth = totalHealth,
            actualHealth = actualHealth,
            battleImage = battleImage,
            healthProgress = if (totalHealth > 0) actualHealth.toFloat() / totalHealth.toFloat() else 0f,
            offensiveMultiplier = 1.0,
            defensiveMultiplier = 0.0,
            damageSkills = damageSkills,
            buffSkills = buffSkills,
            debuffSkills = debuffSkills
        )
    }

    private fun mapCharSkillsToUIModel(battleChar: BattleChar, skills: List<CharSkill>): List<CharSkillUIModel> {
        return skills.map {
            it.toUIModel(
                image = resourcesProvider.getSkillImage(it.imageName) ?: 0,
                currentRefreshTime = 0,
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
}
