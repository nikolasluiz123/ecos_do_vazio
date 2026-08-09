package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.user

import br.com.schmittsolucoes.ecosdovazio.domain.model.User
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository

class InitializeUserUseCase(
    private val userRepository: UserRepository,
    private val identifierProvider: IdentifierProvider,
) {
    suspend fun executeInternal() {
        if (!userRepository.getExistsUser()) {
            val user = User(id = identifierProvider.generate())
            userRepository.insert(user)
        }
    }
}
