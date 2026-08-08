package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHealthData
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharHPUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Long> = flow {
        val userId = userRepository.getFirstUser().id
        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(0)
            return@flow
        }

        val healthFlow = charRepository.getCharHealthData(charId).map {
            val baseValue = getBaseValue(it)
            val multiplier = getMultiplier(it)
            val vitalityPoints = getVitalityPoints(it)

            baseValue + (vitalityPoints * multiplier)
        }

        emitAll(healthFlow)
    }

    private fun getBaseValue(data: CharHealthData): Long {
        return when (data.classCategory) {
            ClassCategory.WARRIOR -> 100L
            ClassCategory.MAGE -> 60L
            ClassCategory.ARCHER -> 80L
        }
    }

    private fun getMultiplier(data: CharHealthData): Long {
        return when (data.classCategory) {
            ClassCategory.WARRIOR -> 10L
            ClassCategory.MAGE -> 4L
            ClassCategory.ARCHER -> 6L
        }
    }

    private fun getVitalityPoints(data: CharHealthData): Long {
        return data.charVitality + data.classIncrementVitality + (data.specializationIncrementVitality ?: 0L)
    }
}