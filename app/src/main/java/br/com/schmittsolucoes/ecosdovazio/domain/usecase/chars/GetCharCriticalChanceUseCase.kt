package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharCriticalData
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharCriticalChanceUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository
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

        val criticalChanceFlow = charRepository.getCharCriticalData(charId).map {
            val factor = getFactor(it.classCategory)
            val maxChance = getMaxResistanceValue(it.classCategory)
            val points = getPoints(it)
            val chance = points * factor

            minOf(maxChance, chance)
        }

        emitAll(criticalChanceFlow)
    }

    private fun getFactor(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.003
            ClassCategory.MAGE -> 0.005
            ClassCategory.ARCHER -> 0.008
        }
    }

    private fun getMaxResistanceValue(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.25
            ClassCategory.MAGE -> 0.4
            ClassCategory.ARCHER -> 0.6
        }
    }

    private fun getPoints(data: CharCriticalData): Long {
        return data.charDexterity + data.classIncrementDexterity + (data.specializationIncrementDexterity ?: 0L)
    }

}