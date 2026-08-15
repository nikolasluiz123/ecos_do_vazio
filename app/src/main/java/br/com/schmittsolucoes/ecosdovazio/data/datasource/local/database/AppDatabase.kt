package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars.CharRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes.ClassRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseInfoRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseMobRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.history.HistoryPhaseRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.LanguageRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.TranslationRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.mobs.MobRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.skills.SkillRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization.SpecializationRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.user.UserRoomDAO
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.converter.Converters
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.ClassEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseInfoEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.HistoryPhaseMobEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.MobEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.SkillEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.SpecializationEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.UserEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.LanguageEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.internacionalization.TranslationIdentifierEntity

@Database(
    version = 6,
    entities = [
        UserEntity::class, CharEntity::class, ClassEntity::class, SpecializationEntity::class,
        LanguageEntity::class, TranslationEntity::class, TranslationIdentifierEntity::class,
        MobEntity::class, HistoryPhaseEntity::class, HistoryPhaseMobEntity::class,
        HistoryPhaseInfoEntity::class, SkillEntity::class
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserRoomDAO
    abstract fun charDao(): CharRoomDAO
    abstract fun classDao(): ClassRoomDAO
    abstract fun specializationDao(): SpecializationRoomDAO
    abstract fun languageDao(): LanguageRoomDAO
    abstract fun translationDao(): TranslationRoomDAO
    abstract fun mobDao(): MobRoomDAO
    abstract fun historyPhaseDao(): HistoryPhaseRoomDAO
    abstract fun historyPhaseMobDao(): HistoryPhaseMobRoomDAO
    abstract fun historyPhaseInfoDao(): HistoryPhaseInfoRoomDAO
    abstract fun skillDao(): SkillRoomDAO
}
