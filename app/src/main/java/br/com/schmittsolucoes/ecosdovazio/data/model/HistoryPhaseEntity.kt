package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity

@Entity(
    tableName = "history_phases",
    foreignKeys = [
        ForeignKey(
            entity = TranslationIdentifierEntity::class,
            parentColumns = ["id"],
            childColumns = ["name_translation_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryPhaseEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("name_translation_id", index = true)
    val nameTranslationId: String,

    @ColumnInfo("phase_number")
    val phaseNumber: Int,
): UniqueEntity
