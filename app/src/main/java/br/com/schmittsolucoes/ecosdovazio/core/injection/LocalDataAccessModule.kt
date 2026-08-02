package br.com.schmittsolucoes.ecosdovazio.core.injection

import android.content.Context
import androidx.room.Room
import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.AppDatabase
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars.CharLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes.ClassLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.LanguageLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.internacionalization.TranslationLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization.SpecializationLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.user.UserLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.transaction.RoomDatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.resources.AndroidResourcesLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.resources.ResourcesLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataAccessModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ecos_do_vazio.db"
        ).fallbackToDestructiveMigration(true)
        //.addMigrations(*DatabaseMigrations.getAll())
        .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseTransaction(db: AppDatabase): DatabaseTransaction {
        return RoomDatabaseTransaction(db)
    }

    @Provides
    fun provideUserLocalDataSource(db: AppDatabase): UserLocalDataSource {
        return db.userDao()
    }

    @Provides
    fun provideCharLocalDataSource(db: AppDatabase): CharLocalDataSource {
        return db.charDao()
    }

    @Provides
    fun provideSpecializationLocalDataSource(db: AppDatabase): SpecializationLocalDataSource {
        return db.specializationDao()
    }

    @Provides
    fun provideClassLocalDataSource(db: AppDatabase): ClassLocalDataSource {
        return db.classDao()
    }

    @Provides
    fun provideLanguageLocalDataSource(db: AppDatabase): LanguageLocalDataSource {
        return db.languageDao()
    }

    @Provides
    fun provideTranslationLocalDataSource(db: AppDatabase): TranslationLocalDataSource {
        return db.translationDao()
    }

    @Provides
    fun provideResourcesLocalDataSource(): ResourcesLocalDataSource {
        return AndroidResourcesLocalDataSource()
    }
}
