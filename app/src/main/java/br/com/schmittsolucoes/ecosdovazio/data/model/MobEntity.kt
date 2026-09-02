package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

@Entity(
    tableName = "mobs",
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
data class MobEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("name_translation_id", index = true)
    val nameTranslationId: String,

    @ColumnInfo("description_translation_id", index = true)
    val descriptionTranslationId: String,

    val strength: Long = 0,

    val dexterity: Long = 0,

    val intelligence: Long = 0,

    @ColumnInfo("physical_resistance")
    val physicalResistance: Long = 0,

    @ColumnInfo("magic_resistance")
    val magicResistance: Long = 0,

    val vitality: Long = 0,

    val agility: Long = 0,

    @ColumnInfo("battle_image_name")
    val battleImageName: String,

    @ColumnInfo("profile_image_name")
    val profileImageName: String,

    @ColumnInfo("mob_category")
    val mobCategory: MobCategory,
): UniqueEntity
