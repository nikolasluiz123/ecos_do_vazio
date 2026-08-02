package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationEntity

@Entity(
    tableName = "specializations",
    foreignKeys = [
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["id"],
            childColumns = ["class_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TranslationEntity::class,
            parentColumns = ["id"],
            childColumns = ["name_translation_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TranslationEntity::class,
            parentColumns = ["id"],
            childColumns = ["description_translation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SpecializationEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("name_translation_id", index = true)
    val nameTranslationId: String,

    @ColumnInfo("description_translation_id", index = true)
    val descriptionTranslationId: String,

    @ColumnInfo("class_id", index = true)
    val classId: String,

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
) : UniqueEntity
