package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CompletedHistoryPhasesCountQueryUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
) {
    operator fun invoke(): Flow<Int> = flow {
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

        emitAll(historyPhaseRepository.getCompletedHistoryPhasesCount(charId))
    }
}
