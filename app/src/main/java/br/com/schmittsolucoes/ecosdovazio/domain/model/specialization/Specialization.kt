package br.com.schmittsolucoes.ecosdovazio.domain.model.specialization

data class Specialization(
    val id: String,
    val nameTranslationId: String,
    val descriptionTranslationId: String,
    val classId: String,
    val images: Images,
    val attributes: Attributes
) {
    data class Images(
        val battleImageName: String,
        val presentationImageName: String,
        val profileImageName: String,
    )

    data class Attributes(
        val incrementStrength: Long = 0,
        val incrementDexterity: Long = 0,
        val incrementIntelligence: Long = 0,
        val incrementPhysicalResistance: Long = 0,
        val incrementMagicResistance: Long = 0,
        val incrementVitality: Long = 0,
        val incrementAgility: Long = 0,
    )
}
