package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

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
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(0)
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(0)
            return@flow
        }

        val healthFlow = charRepository.getCharHealthData(charId).map {
            val vitalityPoints = it.vitality.totalValue
            calculate(it.classCategory, vitalityPoints)
        }

        emitAll(healthFlow)
    }

    fun calculate(classCategory: ClassCategory, vitalityPoints: Long): Long {
        val baseValue = getBaseValue(classCategory)
        val multiplier = getMultiplier(classCategory)

        return baseValue + (vitalityPoints * multiplier)
    }

    private fun getBaseValue(classCategory: ClassCategory): Long {
        return when (classCategory) {
            ClassCategory.WARRIOR -> 220L
            ClassCategory.MAGE -> 200L
            ClassCategory.ARCHER -> 200L
        }
    }

    private fun getMultiplier(classCategory: ClassCategory): Long {
        return when (classCategory) {
            ClassCategory.WARRIOR -> 8L
            ClassCategory.MAGE -> 4L
            ClassCategory.ARCHER -> 6L
        }
    }
}