package br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.schmittsolucoes.ecosdovazio.data.model.UniqueEntity

@Entity(tableName = "translation_identifiers")
data class TranslationIdentifierEntity(
    @PrimaryKey
    override val id: String
): UniqueEntity
