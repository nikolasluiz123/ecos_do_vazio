package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

data class CharAttributes(
    val attributes: List<Attributes>,
    val maxAttributeValue: Long
) {
    data class Attributes(
        val id: AttributeIdentifier,
        val charValue: Long,
        val classValue: Long,
        val specializationValue: Long,
    )

    enum class AttributeIdentifier {
        STRENGTH,
        DEXTERITY,
        INTELLIGENCE,
        PHYSICAL_RESISTANCE,
        MAGIC_RESISTANCE,
        VITALITY,
        AGILITY
    }
}
