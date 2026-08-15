package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharBaseDamageUseCase(
    private val getCharDamageAttributePointsUseCase: GetCharDamageAttributePointsUseCase,
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Long> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(0)
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(0)
            return@flow
        }

        val damageFlow = charRepository.getCharBaseDamageData(charId).map {
            getCharDamageAttributePointsUseCase.executeInternal(
                attributes = it.attributes,
                classCategory = it.classCategory
            )
        }

        emitAll(damageFlow)
    }
}