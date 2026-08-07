package br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences

import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UnselectCharUseCase(
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val userId = userRepository.getFirstUser().id
        preferencesRepository.clearCharSelection(userId)
    }
}
