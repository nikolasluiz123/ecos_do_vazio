package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCharStatusDataUseCase @Inject constructor(
    private val getCharHPUseCase: GetCharHPUseCase,
    private val getCharBaseDamageUseCase: GetCharBaseDamageUseCase,
    private val getCharPhysicalResistanceUseCase: GetCharPhysicalResistanceUseCase,
    private val getCharMagicResistanceUseCase: GetCharMagicResistanceUseCase,
    private val getCharCriticalChanceUseCase: GetCharCriticalChanceUseCase,
    private val getCharDodgeChanceUseCase: GetCharDodgeChanceUseCase,
) {
    operator fun invoke(): Flow<CharStatus> = combine<Any, CharStatus>(
        getCharHPUseCase(),
        getCharBaseDamageUseCase(),
        getCharPhysicalResistanceUseCase(),
        getCharMagicResistanceUseCase(),
        getCharCriticalChanceUseCase(),
        getCharDodgeChanceUseCase(),
    ) { values ->
        CharStatus(
            hp = values[0] as Long,
            baseDamage = values[1] as Long,
            physicalResistance = values[2] as Double,
            magicResistance = values[3] as Double,
            criticalChance = values[4] as Double,
            dodgeChance = values[5] as Double,
        )
    }
}
