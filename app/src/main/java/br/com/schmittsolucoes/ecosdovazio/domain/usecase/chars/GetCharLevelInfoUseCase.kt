package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharLevelInfo
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.constants.GameConstants.BASE_EXPERIENCE_VALUE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlin.math.pow
import kotlin.math.roundToLong

class GetCharLevelInfoUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<CharLevelInfo> = flow {
        val userId = userRepository.getFirstUser().id
        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(CharLevelInfo(1, 0, 0))
            return@flow
        }

        val char = charRepository.getById(charId)
        val factor = getExperienceFactor(char.level)
        val nextLevelExperience = (BASE_EXPERIENCE_VALUE * char.level.toDouble().pow(factor)).roundToLong()
        val infoFlow = charRepository.getCharLevelInfoData(charId, nextLevelExperience)

        emitAll(infoFlow)
    }

    private fun getExperienceFactor(level: Long): Double {
        return when (level) {
            in 2..10 -> 1.5
            in 11..15 -> 1.8
            in 16..25 -> 2.2
            in 26..30 -> 2.5
            else -> 3.0
        }
    }
}