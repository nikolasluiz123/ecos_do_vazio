package br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import br.com.schmittsolucoes.ecosdovazio.data.model.UniqueEntity

@Entity(
    tableName = "translations",
    primaryKeys = ["id", "language_id"],
    indices = [Index("id"), Index("language_id")],
    foreignKeys = [
        ForeignKey(
            entity = TranslationIdentifierEntity::class,
            parentColumns = ["id"],
            childColumns = ["id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["id"],
            childColumns = ["language_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TranslationEntity(
    override val id: String,

    @ColumnInfo("language_id")
    val languageId: String,

    @ColumnInfo("translated_text")
    val translatedText: String,
): UniqueEntity