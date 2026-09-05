package br.com.schmittsolucoes.ecosdovazio.domain.usecase

import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.SpecializationSelection
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SpecializationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SpecializationsQueryUseCase(
    private val specializationRepository: SpecializationRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val languageProvider: LanguageProvider
) {
    operator fun invoke(): Flow<List<SpecializationSelection>> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(emptyList())
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(emptyList())
            return@flow
        }

        val specializationsFlow = specializationRepository.getSpecializationsForSelection(
            charId = charId,
            languageTag = languageProvider.getDeviceTag()
        )

        emitAll(specializationsFlow)
    }
}
