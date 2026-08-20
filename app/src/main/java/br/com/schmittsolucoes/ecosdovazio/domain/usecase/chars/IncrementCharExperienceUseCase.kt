package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.Char
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.IncrementExperienceResult
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.MAX_PLAYER_LEVEL
import javax.inject.Inject

class IncrementCharExperienceUseCase @Inject constructor(
    private val charRepository: CharRepository,
    private val calculateNextLevelExperienceUseCase: CalculateNextLevelExperienceUseCase
) {
    suspend fun executeInternal(char: Char, gainedExperience: Long): IncrementExperienceResult {
        val newExperience = char.experience + gainedExperience
        var newLevel = char.level
        var levelUp = false

        while (newLevel < MAX_PLAYER_LEVEL) {
            val nextLevelThreshold = calculateNextLevelExperienceUseCase.executeInternal(newLevel)

            if (newExperience >= nextLevelThreshold) {
                newLevel++
                levelUp = true
            } else {
                break
            }
        }

        charRepository.update(
            char.copy(
                experience = newExperience,
                level = newLevel
            )
        )

        return IncrementExperienceResult(
            newLevel = newLevel,
            newExperience = newExperience,
            levelUp = levelUp
        )
    }
}
