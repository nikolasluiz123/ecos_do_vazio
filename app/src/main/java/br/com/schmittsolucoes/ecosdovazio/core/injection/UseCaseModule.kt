package br.com.schmittsolucoes.ecosdovazio.core.injection

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.LanguageRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.TranslationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.InitializeDatabaseUseCase
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
}