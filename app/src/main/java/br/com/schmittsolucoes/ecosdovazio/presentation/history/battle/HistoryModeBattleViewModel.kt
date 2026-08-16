package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.CharSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharBattleUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.UseCharSkillUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobLevelUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobSkillBlockedUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.MobsFromPhaseQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharBuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDamageSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDebuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.GetCharSkillBlockedUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.CharSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.MobSkillUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation.HistoryModeBattleRoute
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
    private val getMobLevelUseCase: GetMobLevelUseCase,
    private val getCharHPUseCase: GetCharHPUseCase,
    private val getCharSkillBlockedUseCase: GetCharSkillBlockedUseCase,
    private val useCharSkillUseCase: UseCharSkillUseCase,
    private val getMobSkillBlockedUseCase: GetMobSkillBlockedUseCase,
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
        _charHealth
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

        val uiModelMobs = mapBattleMobsToUIModel(mobs, mobsHealth)
        val uiModelChar = mapBattleCharToUIModel(char, charHealth)
        val selectedMob = uiModelMobs.find { it.id == selectedMobId } ?: uiModelMobs.firstOrNull()

        HistoryModeBattleUIState(
            phaseId = route.phaseId,
            errorMessage = errorMessage,
            isLoading = isLoading,
            mobs = uiModelMobs,
            char = uiModelChar,
            damageSkills = mapCharSkillsToUIModel(char, damageSkills),
            buffSkills = mapCharSkillsToUIModel(char, buffSkills),
            debuffSkills = mapCharSkillsToUIModel(char, debuffSkills),
            selectedMob = selectedMob,
            selectedSkill = selectedSkill,
            mobSkills = selectedMob?.skills ?: emptyList()
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
        return context.getString(R.string.error_unexpected)
    }

    override fun onShowErrorDialog(message: String) {
        _errorMessage.value = message
    }

    fun onDismissErrorDialog() {
        _errorMessage.value = null
    }

    fun onMobClick(mob: BattleMobUIModel) {
        _selectedMobId.value = mob.id
    }

    fun onSkillLongClick(skill: CharSkillUIModel) {
        _selectedSkill.value = skill
    }

    fun onDismissSkillTooltip() {
        _selectedSkill.value = null
    }

    fun onSkillClick(skill: CharSkillUIModel) {
        val state = uiState.value
        val char = state.char ?: return
        val selectedMob = state.selectedMob ?: return

        val result = useCharSkillUseCase(
            skillInfo = skill.toDomainUsedSkillInfo(),
            battleCharInfo = char.toDomainInfo(),
            battleMobInfo = selectedMob.toDomainInfo()
        )

        when (result) {
            is CharSkillUsageResult.CommonDamage -> {
                _mobsHealth.value = _mobsHealth.value.toMutableMap().apply {
                    put(selectedMob.id, result.newEnemyHealth)
                }
            }

            is CharSkillUsageResult.DamageOverTime -> {
                _mobsHealth.value = _mobsHealth.value.toMutableMap().apply {
                    put(selectedMob.id, result.newEnemyHealth)
                }
            }
        }
    }

    private suspend fun mapBattleMobsToUIModel(
        mobs: List<BattleMob>,
        mobsHealth: Map<String, Long>
    ): List<BattleMobUIModel> {
        return mobs.map {
            val totalHealth = getMobHPUseCase(it.mobCategory, it.attributes.vitality)
            val level = getMobLevelUseCase(route.phaseId)
            val actualHealth = mobsHealth[it.id] ?: totalHealth

            it.toUIModel(
                image = resourcesProvider.getBattleMobImage(it.imageName) ?: 0,
                totalHealth = totalHealth,
                actualHealth = actualHealth,
                healthProgress = if (totalHealth > 0) actualHealth.toFloat() / totalHealth.toFloat() else 0f,
                level = level,
                offensiveMultiplier = 1.0,
                defensiveMultiplier = 0.0,
                skills = mapMobSkillsToUIModel(it.skills, level)
            )
        }
    }

    private fun mapBattleCharToUIModel(char: BattleChar, charHealth: Long?): BattleCharUIModel {
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
            defensiveMultiplier = 0.0
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

    private fun mapMobSkillsToUIModel(mobSkills: List<MobSkill>, mobLevel: Long): List<MobSkillUIModel> {
        return mobSkills.map {
            it.toUIModel(
                currentRefreshTime = 0,
                blocked = getMobSkillBlockedUseCase(
                    mobLevel = mobLevel,
                    skillMinLevel = it.minLevel
                )
            )
        }
    }
}
