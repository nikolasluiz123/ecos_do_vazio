package br.com.schmittsolucoes.ecosdovazio.domain.usecase

import br.com.schmittsolucoes.ecosdovazio.domain.model.Char
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.CharException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateNewUserCharUseCase(
    private val userRepository: UserRepository,
    private val charRepository: CharRepository,
    private val identifierProvider: IdentifierProvider
) {

    suspend operator fun invoke(classId: String?, charName: String?): Result<Unit> = withContext(Dispatchers.IO) {
        val user = userRepository.getFirstUser()

        if (classId == null) {
            return@withContext Result.failure(CharException.ClassSelectionRequired())
        }

        if (charName == null) {
            return@withContext Result.failure(CharException.NameRequired())
        }

        if (charName.length > CHAR_NAME_MAX_LENGTH) {
            return@withContext Result.failure(CharException.NameTooLong(CHAR_NAME_MAX_LENGTH))
        }

        val existsWithSameName = charRepository.getExistsByName(charName)

        if (existsWithSameName) {
            return@withContext Result.failure(CharException.DuplicatedName(charName))
        }

        val char = Char(
            id = identifierProvider.generate(),
            name = charName,
            classId = classId,
            userId = user.id
        )

        Result.success(charRepository.insert(char))
    }

    companion object {
        private const val CHAR_NAME_MAX_LENGTH = 512
    }
}