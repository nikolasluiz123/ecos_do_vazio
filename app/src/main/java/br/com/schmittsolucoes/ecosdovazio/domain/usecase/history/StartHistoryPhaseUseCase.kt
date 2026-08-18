package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseInfo
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class StartHistoryPhaseUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val identifierProvider: IdentifierProvider
) {
    suspend operator fun invoke(phaseId: String) {
        val user = userRepository.getFirstUser() ?: throw UserException.UserNotFound()
        val charId = preferencesRepository.getUserPreferences(user.id).firstOrNull()?.selectedCharId!!
        val existingInfo = historyPhaseRepository.getHistoryPhaseInfo(charId, phaseId)

        if (existingInfo != null) {
            historyPhaseRepository.saveHistoryPhaseInfo(
                existingInfo.copy(tryNumber = existingInfo.tryNumber + 1)
            )
        } else {
            historyPhaseRepository.saveHistoryPhaseInfo(
                HistoryPhaseInfo(
                    id = identifierProvider.generate(),
                    charId = charId,
                    phaseId = phaseId,
                    tryNumber = 1
                )
            )
        }
    }
}
