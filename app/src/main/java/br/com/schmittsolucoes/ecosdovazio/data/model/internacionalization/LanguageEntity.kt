package br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.UniqueEntity

@Entity(
    tableName = "languages"
)
data class LanguageEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("is_default")
    val isDefault: Boolean,
): UniqueEntity