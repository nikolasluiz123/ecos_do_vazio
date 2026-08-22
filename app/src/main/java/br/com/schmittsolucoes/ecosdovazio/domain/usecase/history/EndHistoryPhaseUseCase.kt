package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.toXPInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.EndHistoryPhaseResult
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateHistoryPhaseExperienceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.IncrementCharExperienceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.flow.firstOrNull
import java.time.Instant
import javax.inject.Inject

class EndHistoryPhaseUseCase @Inject constructor(
    private val calculateHistoryPhaseExperienceUseCase: CalculateHistoryPhaseExperienceUseCase,
    private val incrementCharExperienceUseCase: IncrementCharExperienceUseCase,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val charRepository: CharRepository,
    private val transaction: DatabaseTransaction
) {
    suspend operator fun invoke(
        phaseId: String,
        battleCharInfo: BattleCharInfo,
        mobs: List<BattleMobInfo>
    ): EndHistoryPhaseResult {
        val charDead = battleCharInfo.actualHealth <= 0
        val allMobsDead = mobs.all { it.actualHealth <= 0 }

        if (charDead || allMobsDead) {
            val user = userRepository.getFirstUser() ?: throw UserException.UserNotFound()
            val charId = preferencesRepository.getUserPreferences(user.id).firstOrNull()?.selectedCharId!!
            val char = charRepository.getById(charId)
            var levelInfo = EndHistoryPhaseResult.LevelInfo(
                currentLevel = char.level,
                levelUp = false
            )

            val existingInfo = historyPhaseRepository.getHistoryPhaseInfo(charId, phaseId)

            if (allMobsDead && existingInfo != null) {
                transaction.run {
                    if (existingInfo.finishedAt == null) {
                        historyPhaseRepository.saveHistoryPhaseInfo(
                            existingInfo.copy(finishedAt = Instant.now())
                        )
                    }

                    levelInfo = incrementCharExperience(mobs, char)
                }
            }

            return EndHistoryPhaseResult(
                isHistoryFinished = true,
                levelInfo = levelInfo
            )
        }

        return EndHistoryPhaseResult(isHistoryFinished = false)
    }

    private suspend fun incrementCharExperience(
        mobs: List<BattleMobInfo>,
        char: Char
    ): EndHistoryPhaseResult.LevelInfo {
        val phaseExperience = calculateHistoryPhaseExperienceUseCase.executeInternal(
            battleMobXPInfo = mobs.map { it.toXPInfo() }
        )

        val result = incrementCharExperienceUseCase.executeInternal(char, phaseExperience)

        return EndHistoryPhaseResult.LevelInfo(
            currentLevel = result.newLevel,
            levelUp = result.levelUp
        )
    }
}
