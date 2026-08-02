package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "chars",
    foreignKeys = [
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["id"],
            childColumns = ["class_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SpecializationEntity::class,
            parentColumns = ["id"],
            childColumns = ["specialization_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CharEntity(
    @PrimaryKey
    override val id: String,

    val name: String,

    val experience: Long,

    @ColumnInfo("class_id", index = true)
    val classId: String,

    @ColumnInfo("user_id", index = true)
    val userId: String,

    @ColumnInfo("specialization_id", index = true)
    val specializationId: String? = null,

    val level: Long = 1,

    val strength: Long = 0,

    val dexterity: Long = 0,

    val intelligence: Long = 0,

    @ColumnInfo("physical_resistance")
    val physicalResistance: Long = 0,

    @ColumnInfo("magic_resistance")
    val magicResistance: Long = 0,

    val vitality: Long = 0,

    val agility: Long = 0,
): UniqueEntity
