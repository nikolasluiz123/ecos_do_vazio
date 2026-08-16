package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateMagicResistanceUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharMagicResistanceUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
    private val calculateMagicResistanceUseCase: CalculateMagicResistanceUseCase,
    private val getCharMagicResistanceFactorUseCase: GetCharMagicResistanceFactorUseCase,
    private val getCharMagicResistanceMaxUseCase: GetCharMagicResistanceMaxUseCase
) {
    operator fun invoke(): Flow<Double> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(0.0)
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(0.0)
            return@flow
        }

        val resistanceFlow = charRepository.getCharMagicResistanceData(charId).map {
            val factor = getCharMagicResistanceFactorUseCase.executeInternal(it.classCategory)
            val maxResistance = getCharMagicResistanceMaxUseCase.executeInternal(it.classCategory)

            calculateMagicResistanceUseCase.executeInternal(
                points = it.magicResistance.totalValue,
                factor = factor,
                maxResistance = maxResistance
            )
        }

        emitAll(resistanceFlow)
    }
}
