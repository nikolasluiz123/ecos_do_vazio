package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculatePhysicalResistanceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharPhysicalResistanceUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
    private val calculatePhysicalResistanceUseCase: CalculatePhysicalResistanceUseCase
) {
    operator fun invoke(): Flow<Double> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(0.0)
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(0.0)
            return@flow
        }

        val resistanceFlow = charRepository.getCharPhysicalResistanceData(charId).map {
            calculatePhysicalResistanceUseCase.executeInternal(
                points = it.physicalResistance.totalValue,
                factor = getFactor(it.classCategory),
                maxResistance = getMaxResistanceValue(it.classCategory)
            )
        }

        emitAll(resistanceFlow)
    }

    private fun getFactor(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 2.0
            ClassCategory.MAGE -> 0.5
            ClassCategory.ARCHER -> 1.0
        }
    }

    private fun getMaxResistanceValue(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.75
            ClassCategory.MAGE -> 0.3
            ClassCategory.ARCHER -> 0.5
        }
    }
}