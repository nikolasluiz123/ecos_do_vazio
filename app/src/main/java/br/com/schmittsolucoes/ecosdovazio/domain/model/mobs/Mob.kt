package br.com.schmittsolucoes.ecosdovazio.domain.model.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

data class Mob(
    val id: String,
    val nameTranslationId: TranslationIdentifier,
    val descriptionTranslationId: TranslationIdentifier,
    val battleImageName: String,
    val profileImageName: String,
    val mobCategory: MobCategory,
    val attributes: Attributes
) {
    data class Attributes(
        val strength: Long = 0,
        val dexterity: Long = 0,
        val intelligence: Long = 0,
        val physicalResistance: Long = 0,
        val magicResistance: Long = 0,
        val vitality: Long = 0,
        val agility: Long = 0,
    )
}
