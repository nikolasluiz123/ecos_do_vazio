package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserCharsQueryUseCase(
    private val charRepository: CharRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<List<CharSelection>> = flow {
        val userId = userRepository.getFirstUser()?.id ?: throw UserException.UserNotFound()

        val charsFlow = charRepository.getUserChars(userId).map { charsList ->
            if (charsList.size < MAX_CHARS_COUNT) {
                charsList + List(MAX_CHARS_COUNT - charsList.size) { CharSelection() }
            } else {
                charsList
            }
        }

        emitAll(charsFlow)
    }

    companion object {
        private const val MAX_CHARS_COUNT = 6
    }
}