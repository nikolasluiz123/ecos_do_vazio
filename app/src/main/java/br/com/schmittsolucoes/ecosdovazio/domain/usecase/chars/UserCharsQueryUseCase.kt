package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharSelection
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserCharsQueryUseCase(
    private val charRepository: CharRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): Flow<List<CharSelection>> = withContext(Dispatchers.IO) {
        val userId = userRepository.getFirstUser().id

        charRepository.getUserChars(userId).map { charsList ->
            if (charsList.size < MAX_CHARS_COUNT) {
                charsList + List(MAX_CHARS_COUNT - charsList.size) { CharSelection() }
            } else {
                charsList
            }
        }
    }

    companion object {
        private const val MAX_CHARS_COUNT = 6
    }
}