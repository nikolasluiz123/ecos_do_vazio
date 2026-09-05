package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetShowSpecializationBannerUseCase(
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val charRepository: CharRepository
) {
    operator fun invoke(): Flow<Boolean> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(false)
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(false)
            return@flow
        }

        val bannerFlow = charRepository.getByIdObservable(charId).map { char ->
            char.level >= MIN_SPECIALIZATION_LEVEL && char.specializationId.isNullOrBlank()
        }

        emitAll(bannerFlow)
    }

    companion object {
        private const val MIN_SPECIALIZATION_LEVEL = 15L
    }
}
