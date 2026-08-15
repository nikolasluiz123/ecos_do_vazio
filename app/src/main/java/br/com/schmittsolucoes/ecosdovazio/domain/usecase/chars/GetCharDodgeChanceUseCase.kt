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

class GetCharDodgeChanceUseCase(
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

        val dodgeChanceFlow = charRepository.getCharDodgeData(charId).map {
            val factor = getFactor(it.classCategory)
            val maxChance = getMaxDodgeValue(it.classCategory)
            val points = it.agility.totalValue
            val chance = points * factor

            minOf(maxChance, chance)
        }

        emitAll(dodgeChanceFlow)
    }

    private fun getFactor(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.005
            ClassCategory.MAGE -> 0.005
            ClassCategory.ARCHER -> 0.012
        }
    }

    private fun getMaxDodgeValue(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.15
            ClassCategory.MAGE -> 0.15
            ClassCategory.ARCHER -> 0.45
        }
    }
}
