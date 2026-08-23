package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.AttributeIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.flow.first

class DecrementAttributeUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(attributeIdentifier: AttributeIdentifier) {
        val userId = userRepository.getFirstUser()?.id ?: throw UserException.UserNotFound()
        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId ?: return

        val char = charRepository.getById(charId)

        val updatedChar = when (attributeIdentifier) {
            AttributeIdentifier.STRENGTH -> char.copy(strength = (char.strength - 1).coerceAtLeast(0))
            AttributeIdentifier.DEXTERITY -> char.copy(dexterity = (char.dexterity - 1).coerceAtLeast(0))
            AttributeIdentifier.INTELLIGENCE -> char.copy(intelligence = (char.intelligence - 1).coerceAtLeast(0))
            AttributeIdentifier.PHYSICAL_RESISTANCE -> char.copy(physicalResistance = (char.physicalResistance - 1).coerceAtLeast(0))
            AttributeIdentifier.MAGIC_RESISTANCE -> char.copy(magicResistance = (char.magicResistance - 1).coerceAtLeast(0))
            AttributeIdentifier.VITALITY -> char.copy(vitality = (char.vitality - 1).coerceAtLeast(0))
            AttributeIdentifier.AGILITY -> char.copy(agility = (char.agility - 1).coerceAtLeast(0))
        }

        charRepository.update(updatedChar)
    }
}
