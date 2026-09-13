package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharHeader
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetCharHeaderUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val charRepository: CharRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<CharHeader?> {
        return userRepository.getFirstUserObservable().flatMapLatest { user ->
            if (user == null) {
                flowOf(null)
            } else {
                preferencesRepository.getUserPreferences(user.id).flatMapLatest { preferences ->
                    val charId = preferences?.selectedCharId

                    if (charId != null) {
                        charRepository.getCharHeader(charId)
                    } else {
                        flowOf(null)
                    }
                }
            }
        }
    }
}
