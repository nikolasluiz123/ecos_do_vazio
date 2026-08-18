package br.com.schmittsolucoes.ecosdovazio.core.injection

import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
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
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.ClassesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateEffectiveDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateMagicResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculatePhysicalResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.CalculateRawDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharBattleUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharDamageReductionUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharSkillDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharSkillRawDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.UseCharSkillUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.ChooseMobSkillUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobAttributesByLevelUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobDamageAttributePointsUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobDamageReductionUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobLevelUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobPointsCountByLevelUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobSkillBlockedUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobSkillDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.GetMobSkillRawDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.MobsFromPhaseQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.RunEnemyRoundUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob.UseMobSkillUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CharAttributesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.CreateNewUserCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetAvailableAttributesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharBaseDamageUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharCriticalChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharDamageAttributePointsUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharDodgeChanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHPUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharHeaderUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharLevelInfoUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharMagicResistanceFactorUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharMagicResistanceMaxUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharMagicResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharPhysicalResistanceFactorUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharPhysicalResistanceMaxUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetCharPhysicalResistanceUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.GetPointsCountByLevelUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.IncrementAttributeUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars.UserCharsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.history.HistoryPhasesQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.InitializeDatabaseUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.InitializeClassesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer.CreateArcherClassUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer.CreateBeastMasterSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer.CreateEngineerSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage.CreateFireMageSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage.CreateMageClassUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage.CreateWaterMageSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior.CreateGladiatorSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior.CreateGuardianSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior.CreateWarriorClassUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.history.InitializeHistoryPhasesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.InitializeMobsUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateCaveOrcMobUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateGoblinHealerMobUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateGoblinShamanMobUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateGoblinWarriorMobUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.translations.InitializeTranslationsUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.user.InitializeUserUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.SelectCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.preferences.UnselectCharUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharBuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDamageSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.CharDebuffSkillsQueryUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills.GetCharSkillBlockedUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideInitializeTranslationsUseCase(
        languageRepository: LanguageRepository,
        translationRepository: TranslationRepository,
        languageProvider: LanguageProvider
    ): InitializeTranslationsUseCase {
        return InitializeTranslationsUseCase(
            languageRepository = languageRepository,
            translationRepository = translationRepository,
            languageProvider = languageProvider
        )
    }

    @Provides
    fun provideInitializeClassesUseCase(
        classRepository: ClassRepository,
        specializationRepository: SpecializationRepository,
        skillRepository: SkillRepository,
        createWarriorClassUseCase: CreateWarriorClassUseCase,
        createGuardianSpecializationUseCase: CreateGuardianSpecializationUseCase,
        createGladiatorSpecializationUseCase: CreateGladiatorSpecializationUseCase,
        createMageClassUseCase: CreateMageClassUseCase,
        createFireMageSpecializationUseCase: CreateFireMageSpecializationUseCase,
        createWaterMageSpecializationUseCase: CreateWaterMageSpecializationUseCase,
        createArcherClassUseCase: CreateArcherClassUseCase,
        createEngineerSpecializationUseCase: CreateEngineerSpecializationUseCase,
        createBeastMasterSpecializationUseCase: CreateBeastMasterSpecializationUseCase,
    ): InitializeClassesUseCase {
        return InitializeClassesUseCase(
            classRepository = classRepository,
            specializationRepository = specializationRepository,
            skillRepository = skillRepository,
            createWarriorClassUseCase = createWarriorClassUseCase,
            createGuardianSpecializationUseCase = createGuardianSpecializationUseCase,
            createGladiatorSpecializationUseCase = createGladiatorSpecializationUseCase,
            createMageClassUseCase = createMageClassUseCase,
            createFireMageSpecializationUseCase = createFireMageSpecializationUseCase,
            createWaterMageSpecializationUseCase = createWaterMageSpecializationUseCase,
            createArcherClassUseCase = createArcherClassUseCase,
            createEngineerSpecializationUseCase = createEngineerSpecializationUseCase,
            createBeastMasterSpecializationUseCase = createBeastMasterSpecializationUseCase,
        )
    }

    @Provides
    fun provideCreateWarriorClassUseCase(
        identifierProvider: IdentifierProvider
    ): CreateWarriorClassUseCase = CreateWarriorClassUseCase(identifierProvider)

    @Provides
    fun provideCreateGuardianSpecializationUseCase(
        identifierProvider: IdentifierProvider
    ): CreateGuardianSpecializationUseCase = CreateGuardianSpecializationUseCase(identifierProvider)

    @Provides
    fun provideCreateGladiatorSpecializationUseCase(
        identifierProvider: IdentifierProvider
    ): CreateGladiatorSpecializationUseCase = CreateGladiatorSpecializationUseCase(identifierProvider)

    @Provides
    fun provideCreateMageClassUseCase(
        identifierProvider: IdentifierProvider
    ): CreateMageClassUseCase = CreateMageClassUseCase(identifierProvider)

    @Provides
    fun provideCreateFireMageSpecializationUseCase(
        identifierProvider: IdentifierProvider
    ): CreateFireMageSpecializationUseCase = CreateFireMageSpecializationUseCase(identifierProvider)

    @Provides
    fun provideCreateWaterMageSpecializationUseCase(
        identifierProvider: IdentifierProvider
    ): CreateWaterMageSpecializationUseCase = CreateWaterMageSpecializationUseCase(identifierProvider)

    @Provides
    fun provideCreateArcherClassUseCase(
        identifierProvider: IdentifierProvider
    ): CreateArcherClassUseCase = CreateArcherClassUseCase(identifierProvider)

    @Provides
    fun provideCreateEngineerSpecializationUseCase(
        identifierProvider: IdentifierProvider
    ): CreateEngineerSpecializationUseCase = CreateEngineerSpecializationUseCase(identifierProvider)

    @Provides
    fun provideCreateBeastMasterSpecializationUseCase(
        identifierProvider: IdentifierProvider
    ): CreateBeastMasterSpecializationUseCase = CreateBeastMasterSpecializationUseCase(identifierProvider)

    @Provides
    fun provideInitializeMobsUseCase(
        mobRepository: MobRepository,
        skillRepository: SkillRepository,
        createGoblinWarriorMobUseCase: CreateGoblinWarriorMobUseCase,
        createGoblinShamanMobUseCase: CreateGoblinShamanMobUseCase,
        createGoblinHealerMobUseCase: CreateGoblinHealerMobUseCase,
        createCaveOrcMobUseCase: CreateCaveOrcMobUseCase
    ): InitializeMobsUseCase {
        return InitializeMobsUseCase(
            mobRepository = mobRepository,
            skillRepository = skillRepository,
            createGoblinWarriorMobUseCase = createGoblinWarriorMobUseCase,
            createGoblinShamanMobUseCase = createGoblinShamanMobUseCase,
            createGoblinHealerMobUseCase = createGoblinHealerMobUseCase,
            createCaveOrcMobUseCase = createCaveOrcMobUseCase
        )
    }

    @Provides
    fun provideCreateGoblinWarriorMobUseCase(
        identifierProvider: IdentifierProvider
    ): CreateGoblinWarriorMobUseCase = CreateGoblinWarriorMobUseCase(identifierProvider)

    @Provides
    fun provideCreateGoblinShamanMobUseCase(
        identifierProvider: IdentifierProvider
    ): CreateGoblinShamanMobUseCase = CreateGoblinShamanMobUseCase(identifierProvider)

    @Provides
    fun provideCreateGoblinHealerMobUseCase(
        identifierProvider: IdentifierProvider
    ): CreateGoblinHealerMobUseCase = CreateGoblinHealerMobUseCase(identifierProvider)

    @Provides
    fun provideCreateCaveOrcMobUseCase(
        identifierProvider: IdentifierProvider
    ): CreateCaveOrcMobUseCase = CreateCaveOrcMobUseCase(identifierProvider)

    @Provides
    fun provideInitializeUserUseCase(
        userRepository: UserRepository,
        identifierProvider: IdentifierProvider
    ): InitializeUserUseCase {
        return InitializeUserUseCase(
            userRepository = userRepository,
            identifierProvider = identifierProvider
        )
    }

    @Provides
    fun provideInitializeDatabaseUseCase(
        initializeUserUseCase: InitializeUserUseCase,
        initializeTranslationsUseCase: InitializeTranslationsUseCase,
        initializeClassesUseCase: InitializeClassesUseCase,
        initializeMobsUseCase: InitializeMobsUseCase,
        initializeHistoryPhasesUseCase: InitializeHistoryPhasesUseCase,
        transaction: DatabaseTransaction
    ): InitializeDatabaseUseCase {
        return InitializeDatabaseUseCase(
            initializeUserUseCase = initializeUserUseCase,
            initializeTranslationsUseCase = initializeTranslationsUseCase,
            initializeClassesUseCase = initializeClassesUseCase,
            initializeMobsUseCase = initializeMobsUseCase,
            initializeHistoryPhasesUseCase = initializeHistoryPhasesUseCase,
            transaction = transaction
        )
    }

    @Provides
    fun provideInitializeHistoryPhasesUseCase(
        historyPhaseRepository: HistoryPhaseRepository,
        identifierProvider: IdentifierProvider
    ): InitializeHistoryPhasesUseCase {
        return InitializeHistoryPhasesUseCase(
            historyPhaseRepository = historyPhaseRepository,
            identifierProvider = identifierProvider
        )
    }

    @Provides
    fun provideHistoryPhasesQueryUseCase(
        historyPhaseRepository: HistoryPhaseRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository,
        languageProvider: LanguageProvider
    ): HistoryPhasesQueryUseCase = HistoryPhasesQueryUseCase(
        historyPhaseRepository = historyPhaseRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository,
        languageProvider = languageProvider
    )

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
        identifierProvider: IdentifierProvider,
        selectCharUseCase: SelectCharUseCase
    ): CreateNewUserCharUseCase = CreateNewUserCharUseCase(
        userRepository = userRepository,
        charRepository = charRepository,
        identifierProvider = identifierProvider,
        selectCharUseCase = selectCharUseCase
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
        getCharDamageAttributePointsUseCase: GetCharDamageAttributePointsUseCase,
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository
    ): GetCharBaseDamageUseCase = GetCharBaseDamageUseCase(
        getCharDamageAttributePointsUseCase = getCharDamageAttributePointsUseCase,
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository
    )

    @Provides
    fun provideCalculatePhysicalResistanceUseCase(): CalculatePhysicalResistanceUseCase {
        return CalculatePhysicalResistanceUseCase()
    }

    @Provides
    fun provideCalculateMagicResistanceUseCase(): CalculateMagicResistanceUseCase {
        return CalculateMagicResistanceUseCase()
    }

    @Provides
    fun provideCalculateEffectiveDamageUseCase(): CalculateEffectiveDamageUseCase {
        return CalculateEffectiveDamageUseCase()
    }

    @Provides
    fun provideGetCharMagicResistanceFactorUseCase(): GetCharMagicResistanceFactorUseCase =
        GetCharMagicResistanceFactorUseCase()

    @Provides
    fun provideGetCharMagicResistanceMaxUseCase(): GetCharMagicResistanceMaxUseCase =
        GetCharMagicResistanceMaxUseCase()

    @Provides
    fun provideGetCharPhysicalResistanceFactorUseCase(): GetCharPhysicalResistanceFactorUseCase =
        GetCharPhysicalResistanceFactorUseCase()

    @Provides
    fun provideGetCharPhysicalResistanceMaxUseCase(): GetCharPhysicalResistanceMaxUseCase =
        GetCharPhysicalResistanceMaxUseCase()

    @Provides
    fun provideGetCharMagicResistanceUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository,
        calculateMagicResistanceUseCase: CalculateMagicResistanceUseCase,
        getCharMagicResistanceFactorUseCase: GetCharMagicResistanceFactorUseCase,
        getCharMagicResistanceMaxUseCase: GetCharMagicResistanceMaxUseCase
    ): GetCharMagicResistanceUseCase = GetCharMagicResistanceUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository,
        calculateMagicResistanceUseCase = calculateMagicResistanceUseCase,
        getCharMagicResistanceFactorUseCase = getCharMagicResistanceFactorUseCase,
        getCharMagicResistanceMaxUseCase = getCharMagicResistanceMaxUseCase
    )

    @Provides
    fun provideGetCharPhysicalResistanceUseCase(
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository,
        userRepository: UserRepository,
        calculatePhysicalResistanceUseCase: CalculatePhysicalResistanceUseCase,
        getCharPhysicalResistanceFactorUseCase: GetCharPhysicalResistanceFactorUseCase,
        getCharPhysicalResistanceMaxUseCase: GetCharPhysicalResistanceMaxUseCase
    ): GetCharPhysicalResistanceUseCase = GetCharPhysicalResistanceUseCase(
        charRepository = charRepository,
        preferencesRepository = preferencesRepository,
        userRepository = userRepository,
        calculatePhysicalResistanceUseCase = calculatePhysicalResistanceUseCase,
        getCharPhysicalResistanceFactorUseCase = getCharPhysicalResistanceFactorUseCase,
        getCharPhysicalResistanceMaxUseCase = getCharPhysicalResistanceMaxUseCase
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

    @Provides
    fun provideMobsFromPhaseQueryUseCase(
        historyPhaseRepository: HistoryPhaseRepository,
        getMobLevelUseCase: GetMobLevelUseCase,
        getMobAttributesByLevelUseCase: GetMobAttributesByLevelUseCase,
        getMobSkillBlockedUseCase: GetMobSkillBlockedUseCase,
        languageProvider: LanguageProvider,
        skillRepository: SkillRepository
    ): MobsFromPhaseQueryUseCase = MobsFromPhaseQueryUseCase(
        historyPhaseRepository = historyPhaseRepository,
        getMobLevelUseCase = getMobLevelUseCase,
        getMobAttributesByLevelUseCase = getMobAttributesByLevelUseCase,
        getMobSkillBlockedUseCase = getMobSkillBlockedUseCase,
        languageProvider = languageProvider,
        skillRepository = skillRepository
    )

    @Provides
    fun provideGetMobSkillBlockedUseCase(): GetMobSkillBlockedUseCase = GetMobSkillBlockedUseCase()

    @Provides
    fun provideChooseMobSkillUseCase(): ChooseMobSkillUseCase = ChooseMobSkillUseCase()

    @Provides
    fun provideRunEnemyRoundUseCase(
        chooseMobSkillUseCase: ChooseMobSkillUseCase,
        useMobSkillUseCase: UseMobSkillUseCase
    ): RunEnemyRoundUseCase = RunEnemyRoundUseCase(
        chooseMobSkillUseCase = chooseMobSkillUseCase,
        useMobSkillUseCase = useMobSkillUseCase
    )

    @Provides
    fun provideGetMobLevelUseCase(
        historyPhaseRepository: HistoryPhaseRepository
    ): GetMobLevelUseCase = GetMobLevelUseCase(
        historyPhaseRepository = historyPhaseRepository
    )

    @Provides
    fun provideGetMobPointsCountByLevelUseCase(
        getPointsCountByLevelUseCase: GetPointsCountByLevelUseCase
    ): GetMobPointsCountByLevelUseCase = GetMobPointsCountByLevelUseCase(
        getPointsCountByLevelUseCase = getPointsCountByLevelUseCase
    )

    @Provides
    fun provideGetMobAttributesByLevelUseCase(
        getMobPointsCountByLevelUseCase: GetMobPointsCountByLevelUseCase
    ): GetMobAttributesByLevelUseCase = GetMobAttributesByLevelUseCase(
        getMobPointsCountByLevelUseCase = getMobPointsCountByLevelUseCase
    )

    @Provides
    fun provideGetMobHPUseCase(): GetMobHPUseCase {
        return GetMobHPUseCase()
    }

    @Provides
    fun provideGetMobDamageReductionUseCase(
        calculatePhysicalResistanceUseCase: CalculatePhysicalResistanceUseCase,
        calculateMagicResistanceUseCase: CalculateMagicResistanceUseCase
    ): GetMobDamageReductionUseCase {
        return GetMobDamageReductionUseCase(
            calculatePhysicalResistanceUseCase = calculatePhysicalResistanceUseCase,
            calculateMagicResistanceUseCase = calculateMagicResistanceUseCase
        )
    }

    @Provides
    fun provideGetCharDamageReductionUseCase(
        calculatePhysicalResistanceUseCase: CalculatePhysicalResistanceUseCase,
        calculateMagicResistanceUseCase: CalculateMagicResistanceUseCase,
        getCharPhysicalResistanceFactorUseCase: GetCharPhysicalResistanceFactorUseCase,
        getCharPhysicalResistanceMaxUseCase: GetCharPhysicalResistanceMaxUseCase,
        getCharMagicResistanceFactorUseCase: GetCharMagicResistanceFactorUseCase,
        getCharMagicResistanceMaxUseCase: GetCharMagicResistanceMaxUseCase
    ): GetCharDamageReductionUseCase {
        return GetCharDamageReductionUseCase(
            calculatePhysicalResistanceUseCase = calculatePhysicalResistanceUseCase,
            calculateMagicResistanceUseCase = calculateMagicResistanceUseCase,
            getCharPhysicalResistanceFactorUseCase = getCharPhysicalResistanceFactorUseCase,
            getCharPhysicalResistanceMaxUseCase = getCharPhysicalResistanceMaxUseCase,
            getCharMagicResistanceFactorUseCase = getCharMagicResistanceFactorUseCase,
            getCharMagicResistanceMaxUseCase = getCharMagicResistanceMaxUseCase
        )
    }

    @Provides
    fun provideGetCharBattleUseCase(
        userRepository: UserRepository,
        charRepository: CharRepository,
        preferencesRepository: PreferencesRepository
    ): GetCharBattleUseCase {
        return GetCharBattleUseCase(
            userRepository = userRepository,
            charRepository = charRepository,
            preferencesRepository = preferencesRepository
        )
    }

    @Provides
    fun provideCharDamageSkillsQueryUseCase(
        skillRepository: SkillRepository,
        userRepository: UserRepository,
        preferencesRepository: PreferencesRepository,
        charRepository: CharRepository,
        languageProvider: LanguageProvider,
    ): CharDamageSkillsQueryUseCase = CharDamageSkillsQueryUseCase(
        skillRepository = skillRepository,
        userRepository = userRepository,
        preferencesRepository = preferencesRepository,
        charRepository = charRepository,
        languageProvider = languageProvider,
    )

    @Provides
    fun provideCharBuffAndDebuffSkillsQueryUseCase(
        skillRepository: SkillRepository,
        userRepository: UserRepository,
        preferencesRepository: PreferencesRepository,
        charRepository: CharRepository,
        languageProvider: LanguageProvider,
    ): CharBuffSkillsQueryUseCase = CharBuffSkillsQueryUseCase(
        skillRepository = skillRepository,
        userRepository = userRepository,
        preferencesRepository = preferencesRepository,
        charRepository = charRepository,
        languageProvider = languageProvider,
    )

    @Provides
    fun provideGetCharSkillBlockedUseCase(): GetCharSkillBlockedUseCase {
        return GetCharSkillBlockedUseCase()
    }

    @Provides
    fun provideGetCharDamageAttributePointsUseCase(): GetCharDamageAttributePointsUseCase {
        return GetCharDamageAttributePointsUseCase()
    }

    @Provides
    fun provideGetCharSkillRawDamageUseCase(
        getCharDamageAttributePointsUseCase: GetCharDamageAttributePointsUseCase,
        calculateRawDamageUseCase: CalculateRawDamageUseCase
    ): GetCharSkillRawDamageUseCase {
        return GetCharSkillRawDamageUseCase(
            getCharDamageAttributePointsUseCase = getCharDamageAttributePointsUseCase,
            calculateRawDamageUseCase = calculateRawDamageUseCase
        )
    }

    @Provides
    fun provideGetCharSkillDamageUseCase(
        getCharSkillRawDamageUseCase: GetCharSkillRawDamageUseCase,
        getMobDamageReductionUseCase: GetMobDamageReductionUseCase,
        calculateEffectiveDamageUseCase: CalculateEffectiveDamageUseCase
    ): GetCharSkillDamageUseCase {
        return GetCharSkillDamageUseCase(
            getCharSkillRawDamageUseCase = getCharSkillRawDamageUseCase,
            getMobDamageReductionUseCase = getMobDamageReductionUseCase,
            calculateEffectiveDamageUseCase = calculateEffectiveDamageUseCase
        )
    }

    @Provides
    fun provideUseCharSkillUseCase(
        getCharSkillDamageUseCase: GetCharSkillDamageUseCase
    ): UseCharSkillUseCase {
        return UseCharSkillUseCase(
            getCharSkillDamageUseCase = getCharSkillDamageUseCase
        )
    }

    @Provides
    fun provideCharDebuffSkillsQueryUseCase(
        skillRepository: SkillRepository,
        userRepository: UserRepository,
        preferencesRepository: PreferencesRepository,
        charRepository: CharRepository,
        languageProvider: LanguageProvider,
    ): CharDebuffSkillsQueryUseCase {
        return CharDebuffSkillsQueryUseCase(
            skillRepository = skillRepository,
            userRepository = userRepository,
            preferencesRepository = preferencesRepository,
            charRepository = charRepository,
            languageProvider = languageProvider,
        )
    }

    @Provides
    fun provideCalculateRawDamageUseCase(): CalculateRawDamageUseCase = CalculateRawDamageUseCase()

    @Provides
    fun provideGetMobDamageAttributePointsUseCase(
        getMobAttributesByLevelUseCase: GetMobAttributesByLevelUseCase
    ): GetMobDamageAttributePointsUseCase = GetMobDamageAttributePointsUseCase(getMobAttributesByLevelUseCase)

    @Provides
    fun provideGetMobSkillRawDamageUseCase(
        getMobDamageAttributePointsUseCase: GetMobDamageAttributePointsUseCase,
        calculateRawDamageUseCase: CalculateRawDamageUseCase
    ): GetMobSkillRawDamageUseCase = GetMobSkillRawDamageUseCase(
        getMobDamageAttributePointsUseCase = getMobDamageAttributePointsUseCase,
        calculateRawDamageUseCase = calculateRawDamageUseCase
    )

    @Provides
    fun provideGetMobSkillDamageUseCase(
        getMobSkillRawDamageUseCase: GetMobSkillRawDamageUseCase,
        getCharDamageReductionUseCase: GetCharDamageReductionUseCase,
        calculateEffectiveDamageUseCase: CalculateEffectiveDamageUseCase
    ): GetMobSkillDamageUseCase = GetMobSkillDamageUseCase(
        getMobSkillRawDamageUseCase = getMobSkillRawDamageUseCase,
        getCharDamageReductionUseCase = getCharDamageReductionUseCase,
        calculateEffectiveDamageUseCase = calculateEffectiveDamageUseCase
    )

    @Provides
    fun provideUseMobSkillUseCase(
        getMobSkillDamageUseCase: GetMobSkillDamageUseCase
    ): UseMobSkillUseCase = UseMobSkillUseCase(getMobSkillDamageUseCase)
}
