package br.com.schmittsolucoes.ecosdovazio.core.injection

import android.content.Context
import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.LanguageRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.TranslationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.ClassesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.InitializeDatabaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CharAttributesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CreateNewUserCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetAvailableAttributesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharBaseDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharCriticalChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharDodgeChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHeaderUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharLevelInfoUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharMagicResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharPhysicalResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetPointsCountByLevelUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.IncrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.UserCharsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.SelectCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.UnselectCharUseCase
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

    @Provides
    fun provideGetCharHPUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharHPUseCase = GetCharHPUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideGetCharHeaderUseCase(
        userRepository: UserRepository,
        preferencesRepository: PreferencesRepository,
        charRepository: CharRepository
    ): GetCharHeaderUseCase = GetCharHeaderUseCase(
        userRepository = userRepository,
        preferencesRepository = preferencesRepository,
        charRepository = charRepository
    )

    @Provides
    fun provideGetCharLevelInfoUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharLevelInfoUseCase = GetCharLevelInfoUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideGetCharBaseDamageUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharBaseDamageUseCase = GetCharBaseDamageUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideGetCharMagicResistanceUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharMagicResistanceUseCase = GetCharMagicResistanceUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideGetCharPhysicalResistanceUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharPhysicalResistanceUseCase = GetCharPhysicalResistanceUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideGetCharCriticalChanceUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharCriticalChanceUseCase = GetCharCriticalChanceUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideGetCharDodgeChanceUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharDodgeChanceUseCase = GetCharDodgeChanceUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideGetPointsCountByLevelUseCase(): GetPointsCountByLevelUseCase = GetPointsCountByLevelUseCase()

    @Provides
    fun provideCharAttributesQueryUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository,
        getPointsCountByLevelUseCase: GetPointsCountByLevelUseCase
    ): CharAttributesQueryUseCase = CharAttributesQueryUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository,
        getPointsCountByLevelUseCase = getPointsCountByLevelUseCase
    )

    @Provides
    fun provideGetAvailableAttributesUseCase(
        getPointsCountByLevelUseCase: GetPointsCountByLevelUseCase,
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetAvailableAttributesUseCase = GetAvailableAttributesUseCase(
        getPointsCountByLevelUseCase = getPointsCountByLevelUseCase,
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideIncrementAttributeUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): IncrementAttributeUseCase = IncrementAttributeUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideSelectCharUseCase(
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): SelectCharUseCase = SelectCharUseCase(
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideUnselectCharUseCase(
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): UnselectCharUseCase = UnselectCharUseCase(
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )
}