package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.LastUnfinishedHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetLastUnfinishedHistoryPhaseUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val languageProvider: LanguageProvider,
) {
    operator fun invoke(): Flow<LastUnfinishedHistoryPhase?> = flow {
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

        val languageTag = languageProvider.getDeviceTag()
        emitAll(historyPhaseRepository.getLastUnfinishedHistoryPhase(charId, languageTag))
    }
}
