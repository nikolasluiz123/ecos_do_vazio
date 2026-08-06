package br.com.schmittsolucoes.ecosdovazio.core.injection

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.LanguageRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.TranslationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.ClassesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.InitializeDatabaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CreateNewUserCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.UserCharsQueryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideInitializeDatabaseUseCase(
        @ApplicationContext context: Context,
        languageRepository: LanguageRepository,
        translationRepository: TranslationRepository,
        userRepository: UserRepository,
        classRepository: ClassRepository,
        languageProvider: LanguageProvider,
        identifierProvider: IdentifierProvider,
        transaction: DatabaseTransaction
    ): InitializeDatabaseUseCase {
        return InitializeDatabaseUseCase(
            context = context,
            languageRepository = languageRepository,
            translationRepository = translationRepository,
            userRepository = userRepository,
            classRepository = classRepository,
            languageProvider = languageProvider,
            identifierProvider = identifierProvider,
            transaction = transaction
        )
    }

    @Provides
    fun provideClassesQueryUseCase(
        classRepository: ClassRepository,
        languageProvider: LanguageProvider
    ): ClassesQueryUseCase = ClassesQueryUseCase(
        classRepository = classRepository,
        languageProvider = languageProvider
    )

    @Provides
    fun provideCreateNewUserCharUseCase(
        userRepository: UserRepository,
        charRepository: CharRepository,
        identifierProvider: IdentifierProvider
    ): CreateNewUserCharUseCase = CreateNewUserCharUseCase(
        userRepository = userRepository,
        charRepository = charRepository,
        identifierProvider = identifierProvider
    )

    @Provides
    fun provideUserCharsQueryUseCase(
        charRepository: CharRepository,
        userRepository: UserRepository
    ): UserCharsQueryUseCase = UserCharsQueryUseCase(
        charRepository = charRepository,
        userRepository = userRepository
    )
}