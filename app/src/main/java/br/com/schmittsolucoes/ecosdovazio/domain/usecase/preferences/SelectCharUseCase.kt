package br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences

import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SelectCharUseCase(
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(charId: String) = withContext(Dispatchers.IO) {
        val userId = userRepository.getFirstUser()?.id ?: throw UserException.UserNotFound()
        preferencesRepository.saveCharSelection(userId, charId)
    }
}
