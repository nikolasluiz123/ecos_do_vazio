package br.com.schmittsolucoes.ecosdovazio.presentation.history.battle

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharBattleUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobLevelUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.MobsFromPhaseQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHPUseCase
import br.com.schmittsolucoes.ecosdovazio.presentation.CommonViewModel
import br.com.schmittsolucoes.ecosdovazio.presentation.STATE_IN_STOP_TIMEOUT_MILLIS
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleCharUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.model.BattleMobUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.battle.navigation.HistoryModeBattleRoute
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
    savedStateHandle: SavedStateHandle,
    mobsFromPhaseQueryUseCase: MobsFromPhaseQueryUseCase,
    getCharBattleUseCase: GetCharBattleUseCase
) : CommonViewModel() {

    private val route = savedStateHandle.toRoute<HistoryModeBattleRoute>()

    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<HistoryModeBattleUIState> = combine(
        _errorMessage,
        _isLoading,
        mobsFromPhaseQueryUseCase(route.phaseId),
        getCharBattleUseCase()
    ) { errorMessage, isLoading, mobs, char ->
        HistoryModeBattleUIState(
            phaseId = route.phaseId,
            errorMessage = errorMessage,
            isLoading = isLoading,
            mobs = mapBattleMobsToUIModel(mobs),
            char = mapBattleCharToUIModel(char)
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

    private suspend fun mapBattleMobsToUIModel(mobs: List<BattleMob>): List<BattleMobUIModel> {
        return mobs.map {
            val totalHealth = getMobHPUseCase(it.mobCategory, it.attributes.vitality)
            val level = getMobLevelUseCase(route.phaseId)

            it.toUIModel(
                image = resourcesProvider.getBattleMobImage(it.imageName) ?: 0,
                totalHealth = totalHealth,
                actualHealth = totalHealth,
                healthProgress = if (totalHealth > 0) totalHealth.toFloat() / totalHealth.toFloat() else 0f,
                level = level
            )
        }
    }

    private fun mapBattleCharToUIModel(char: BattleChar): BattleCharUIModel {
        val totalHealth = getCharHPUseCase.calculate(
            classCategory = char.classCategory,
            vitalityPoints = char.vitality.value
        )

        val battleImage = resourcesProvider.getBattleClassImage(char.battleImageName)
            ?: resourcesProvider.getBattleSpecializationImage(char.battleImageName)
            ?: 0

        return char.toUIModel(
            totalHealth = totalHealth,
            actualHealth = totalHealth,
            battleImage = battleImage,
            healthProgress = if (totalHealth > 0) totalHealth.toFloat() / totalHealth.toFloat() else 0f
        )
    }
}
