package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars.CharRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes.ClassRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.LanguageRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.TranslationRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization.SpecializationRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.user.UserRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.ClassEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.SpecializationEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.UserEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.LanguageEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity

@Database(
    version = 2,
    entities = [
        UserEntity::class, CharEntity::class, ClassEntity::class, SpecializationEntity::class,
        LanguageEntity::class, TranslationEntity::class, TranslationIdentifierEntity::class
    ],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserRoomDAO
    abstract fun charDao(): CharRoomDAO
    abstract fun classDao(): ClassRoomDAO
    abstract fun specializationDao(): SpecializationRoomDAO
    abstract fun languageDao(): LanguageRoomDAO
    abstract fun translationDao(): TranslationRoomDAO
}
