package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.CharAttributes
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions.UserException
import kotlinx.coroutines.flow.first

class IncrementAttributeUseCase(
    private val charRepository: CharRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(attributeIdentifier: CharAttributes.AttributeIdentifier) {
        val userId = userRepository.getFirstUser()?.id ?: throw UserException.UserNotFound()
        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId ?: return

        val char = charRepository.getById(charId)

        val updatedChar = when (attributeIdentifier) {
            CharAttributes.AttributeIdentifier.STRENGTH -> char.copy(strength = char.strength + 1)
            CharAttributes.AttributeIdentifier.DEXTERITY -> char.copy(dexterity = char.dexterity + 1)
            CharAttributes.AttributeIdentifier.INTELLIGENCE -> char.copy(intelligence = char.intelligence + 1)
            CharAttributes.AttributeIdentifier.PHYSICAL_RESISTANCE -> char.copy(physicalResistance = char.physicalResistance + 1)
            CharAttributes.AttributeIdentifier.MAGIC_RESISTANCE -> char.copy(magicResistance = char.magicResistance + 1)
            CharAttributes.AttributeIdentifier.VITALITY -> char.copy(vitality = char.vitality + 1)
            CharAttributes.AttributeIdentifier.AGILITY -> char.copy(agility = char.agility + 1)
        }

        charRepository.update(updatedChar)
    }
}
