package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetAvailableAttributesUseCase(
    private val getPointsCountByLevelUseCase: GetPointsCountByLevelUseCase,
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
) {
    operator fun invoke(): Flow<Long> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(0L)
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(0L)
            return@flow
        }

        val char = charRepository.getById(charId)
        val totalGrantedAttributes = getTotalGrantedAttributes(char.level)
        val totalCharAttributes = getTotalCharAttributes(char)

        emit(totalGrantedAttributes - totalCharAttributes)
    }

    private fun getTotalGrantedAttributes(charLevel: Long): Long {
        var totalPoints = 0L

        for (i in 1..charLevel) {
            totalPoints += getPointsCountByLevelUseCase.executeInternal(i)
        }

        return totalPoints
    }

    private fun getTotalCharAttributes(char: Char): Long {
        return char.strength +
                char.dexterity +
                char.intelligence +
                char.physicalResistance +
                char.magicResistance +
                char.vitality +
                char.agility
    }
}