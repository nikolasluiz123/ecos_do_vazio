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

    val multiplier: Double? = null,

    val duration: Int? = null,

    @ColumnInfo("refresh_time")
    val refreshTime: Int,

    @ColumnInfo("min_level")
    val minLevel: Long,

    @ColumnInfo("image_name")
    val imageName: String? = null,
): UniqueEntity
