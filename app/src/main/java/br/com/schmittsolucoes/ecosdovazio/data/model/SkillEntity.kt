package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory

@Entity(
    tableName = "skills",
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
        ),
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["id"],
            childColumns = ["class_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SpecializationEntity::class,
            parentColumns = ["id"],
            childColumns = ["specialization_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MobEntity::class,
            parentColumns = ["id"],
            childColumns = ["mob_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SkillEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("name_translation_id", index = true)
    val nameTranslationId: String,

    @ColumnInfo("description_translation_id", index = true)
    val descriptionTranslationId: String,

    @ColumnInfo("skill_category")
    val skillCategory: SkillCategory,

    @ColumnInfo("class_id", index = true)
    val classId: String? = null,

    @ColumnInfo("specialization_id", index = true)
    val specializationId: String? = null,

    @ColumnInfo("mob_id", index = true)
    val mobId: String? = null,

    val damage: Long? = null,

    @ColumnInfo("life_restore")
    val lifeRestore: Long? = null,

    val multiplier: Double? = null,

    val duration: Int? = null,

    @ColumnInfo("refresh_time")
    val refreshTime: Int,

    @ColumnInfo("min_level")
    val minLevel: Long,

    @ColumnInfo("required_strength")
    val requiredStrength: Long = 0,

    @ColumnInfo("required_dexterity")
    val requiredDexterity: Long = 0,

    @ColumnInfo("required_intelligence")
    val requiredIntelligence: Long = 0,

    @ColumnInfo("required_physical_resistance")
    val requiredPhysicalResistance: Long = 0,

    @ColumnInfo("required_magic_resistance")
    val requiredMagicResistance: Long = 0,

    @ColumnInfo("required_vitality")
    val requiredVitality: Long = 0,

    @ColumnInfo("required_agility")
    val requiredAgility: Long = 0,

    @ColumnInfo("image_name")
    val imageName: String,
): UniqueEntity
