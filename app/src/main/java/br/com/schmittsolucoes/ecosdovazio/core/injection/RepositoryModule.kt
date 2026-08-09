package br.com.schmittsolucoes.ecosdovazio.core.injection

import br.com.schmittsolucoes.ecosdovazio.data.repository.CharRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.ClassRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.DataStorePreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.data.repository.HistoryPhaseRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.LanguageRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.MobRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.SkillRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.SpecializationRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.TranslationRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.data.repository.UserRepositoryImpl
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.LanguageRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.MobRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SpecializationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.TranslationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindClassRepository(
        classRepositoryImpl: ClassRepositoryImpl
    ): ClassRepository

    @Binds
    @Singleton
    abstract fun bindLanguageRepository(
        languageRepositoryImpl: LanguageRepositoryImpl
    ): LanguageRepository

    @Binds
    @Singleton
    abstract fun bindTranslationRepository(
        translationRepositoryImpl: TranslationRepositoryImpl
    ): TranslationRepository

    @Binds
    @Singleton
    abstract fun bindCharRepository(
        charRepositoryImpl: CharRepositoryImpl
    ): CharRepository

    @Binds
    @Singleton
    abstract fun bindSpecializationRepository(
        specializationRepositoryImpl: SpecializationRepositoryImpl
    ): SpecializationRepository

    @Binds
    @Singleton
    abstract fun bindSkillRepository(
        skillRepositoryImpl: SkillRepositoryImpl
    ): SkillRepository

    @Binds
    @Singleton
    abstract fun bindMobRepository(
        mobRepositoryImpl: MobRepositoryImpl
    ): MobRepository

    @Binds
    @Singleton
    abstract fun bindHistoryPhaseRepository(
        historyPhaseRepositoryImpl: HistoryPhaseRepositoryImpl
    ): HistoryPhaseRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        preferencesRepositoryImpl: DataStorePreferencesRepository
    ): PreferencesRepository
}
