package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.CharException
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class SetCharSpecializationUseCase(
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val charRepository: CharRepository
) {
    suspend operator fun invoke(specializationId: String?): Result<Unit> = withContext(IO) {
        val user = userRepository.getFirstUser() ?: return@withContext Result.failure(
            UserException.UserNotFound()
        )

        if (specializationId.isNullOrBlank()) {
            return@withContext Result.failure(CharException.SpecializationSelectionRequired())
        }

        val charId = preferencesRepository.getUserPreferences(user.id).firstOrNull()?.selectedCharId
            ?: return@withContext Result.failure(UserException.UserNotFound())

        val char = charRepository.getById(charId)

        val updatedChar = char.copy(specializationId = specializationId)
        charRepository.update(updatedChar)

        Result.success(Unit)
    }
}
