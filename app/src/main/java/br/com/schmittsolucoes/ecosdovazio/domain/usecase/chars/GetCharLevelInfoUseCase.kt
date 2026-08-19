package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetCharLevelInfoUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
    private val calculateNextLevelExperienceUseCase: CalculateNextLevelExperienceUseCase
) {
    operator fun invoke(): Flow<CharLevelInfo> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(CharLevelInfo(1, 0, 0))
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(CharLevelInfo(1, 0, 0))
            return@flow
        }

        val char = charRepository.getById(charId)
        val nextLevelExperience = calculateNextLevelExperienceUseCase.executeInternal(char.level)
        val infoFlow = charRepository.getCharLevelInfoData(charId, nextLevelExperience)

        emitAll(infoFlow)
    }
}