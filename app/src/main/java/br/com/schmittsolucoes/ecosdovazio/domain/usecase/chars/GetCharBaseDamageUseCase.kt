package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharBaseDamageData
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharBaseDamageUseCase(
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

        val damageFlow = charRepository.getCharBaseDamageData(charId).map {
            getPointsByCategory(it)
        }

        emitAll(damageFlow)
    }

    private fun getPointsByCategory(data: CharBaseDamageData): Long {
        return when (data.classCategory) {
            ClassCategory.WARRIOR -> {
                data.strength.charValue + data.strength.classValue + (data.strength.specializationValue ?: 0L)
            }

            ClassCategory.MAGE -> {
                data.intelligence.charValue + data.intelligence.classValue + (data.intelligence.specializationValue ?: 0L)
            }

            ClassCategory.ARCHER -> {
                data.dexterity.charValue + data.dexterity.classValue + (data.dexterity.specializationValue ?: 0L)
            }
        }
    }
}