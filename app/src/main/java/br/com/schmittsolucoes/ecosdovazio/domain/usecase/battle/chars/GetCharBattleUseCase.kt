package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleChar
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetCharBattleUseCase(
    private val userRepository: UserRepository,
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val getCharHPUseCase: GetCharHPUseCase
) {
    operator fun invoke(): Flow<BattleChar> = flow {
        val userId = userRepository.getFirstUser()?.id ?: throw UserException.UserNotFound()
        val preferences = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId!!
        val flow = charRepository.getBattleChar(preferences).map { char ->
            val totalHealth = getCharHPUseCase.calculate(char.classCategory, char.vitality.totalValue)
            char.copy(totalHealth = totalHealth, actualHealth = totalHealth)
        }

        emitAll(flow)
    }
}