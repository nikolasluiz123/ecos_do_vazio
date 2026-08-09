package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharPhysicalResistanceData
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.PHYSICAL_RESISTANCE_SCALE_CONSTANT
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharPhysicalResistanceUseCase(
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

        val resistanceFlow = charRepository.getCharPhysicalResistanceData(charId).map {
            val factor = getFactor(it.classCategory)
            val maxResistance = getMaxResistanceValue(it.classCategory)
            val points = getPoints(it)
            val effectiveResistance = points * factor
            val calculatedResistance = (effectiveResistance / (PHYSICAL_RESISTANCE_SCALE_CONSTANT + effectiveResistance))

            minOf(maxResistance, calculatedResistance)
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

    private fun getPoints(data: CharPhysicalResistanceData): Long {
        return data.charPhysicalResistance + data.classIncrementPhysicalResistance + (data.specializationIncrementPhysicalResistance ?: 0L)
    }
}