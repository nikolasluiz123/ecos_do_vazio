package br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.UniqueEntity

@Entity(
    tableName = "translations",
    foreignKeys = [
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["id"],
            childColumns = ["language_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TranslationEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("language_id", index = true)
    val languageId: String,

    @ColumnInfo("translated_text")
    val translatedText: String,
): UniqueEntity