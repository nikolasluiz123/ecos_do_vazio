package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.flow.firstOrNull
import java.time.Instant
import javax.inject.Inject

class EndHistoryPhaseUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val historyPhaseRepository: HistoryPhaseRepository
) {
    suspend operator fun invoke(
        phaseId: String,
        battleCharInfo: BattleCharInfo,
        mobs: List<BattleMobInfo>
    ): Boolean {
        val charDead = battleCharInfo.actualHealth <= 0
        val allMobsDead = mobs.all { it.actualHealth <= 0 }

        if (charDead || allMobsDead) {
            val user = userRepository.getFirstUser() ?: throw UserException.UserNotFound()
            val charId = preferencesRepository.getUserPreferences(user.id).firstOrNull()?.selectedCharId!!
            val existingInfo = historyPhaseRepository.getHistoryPhaseInfo(charId, phaseId)

            if (allMobsDead && existingInfo != null && existingInfo.finishedAt == null) {
                historyPhaseRepository.saveHistoryPhaseInfo(
                    existingInfo.copy(finishedAt = Instant.now())
                )
            }

            return true
        }

        return false
    }
}
