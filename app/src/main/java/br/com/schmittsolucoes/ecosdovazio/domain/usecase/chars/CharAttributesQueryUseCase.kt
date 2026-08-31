package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.MAX_PLAYER_LEVEL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CharAttributesQueryUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
    private val getTotalPointsCountUseCase: GetTotalPointsCountUseCase
) {
    operator fun invoke(): Flow<CharAttributes?> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(null)
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(null)
            return@flow
        }

        val maxAttributes = getTotalPointsCountUseCase.executeInternal(MAX_PLAYER_LEVEL)
        val attributesFlow = charRepository.getCharAttributesData(charId, maxAttributes)

        emitAll(attributesFlow)
    }
}