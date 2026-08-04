package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

@Entity(
    tableName = "classes",
    foreignKeys = [
        ForeignKey(
            entity = TranslationIdentifierEntity::class,
            parentColumns = ["id"],
            childColumns = ["name_translation_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TranslationIdentifierEntity::class,
            parentColumns = ["id"],
            childColumns = ["description_translation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ClassEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("name_translation_id", index = true)
    val nameTranslationId: String,

    @ColumnInfo("description_translation_id", index = true)
    val descriptionTranslationId: String,

    @ColumnInfo("class_category")
    val classCategory: ClassCategory,

    @ColumnInfo("battle_image_name")
    val battleImageName: String,

    @ColumnInfo("presentation_image_name")
    val presentationImageName: String,

    @ColumnInfo("increment_strength")
    val incrementStrength: Long = 0,

    @ColumnInfo("increment_dexterity")
    val incrementDexterity: Long = 0,

    @ColumnInfo("increment_intelligence")
    val incrementIntelligence: Long = 0,

    @ColumnInfo("increment_physical_resistance")
    val incrementPhysicalResistance: Long = 0,

    @ColumnInfo("increment_magic_resistance")
    val incrementMagicResistance: Long = 0,

    @ColumnInfo("increment_vitality")
    val incrementVitality: Long = 0,

    @ColumnInfo("increment_agility")
    val incrementAgility: Long = 0,
): UniqueEntity
