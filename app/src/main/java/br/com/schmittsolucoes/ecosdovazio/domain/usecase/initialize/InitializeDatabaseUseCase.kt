package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize

import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.InitializeClassesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.history.InitializeHistoryPhasesUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.InitializeMobsUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.translations.InitializeTranslationsUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.user.InitializeUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InitializeDatabaseUseCase(
    private val initializeUserUseCase: InitializeUserUseCase,
    private val initializeTranslationsUseCase: InitializeTranslationsUseCase,
    private val initializeClassesUseCase: InitializeClassesUseCase,
    private val initializeMobsUseCase: InitializeMobsUseCase,
    private val initializeHistoryPhasesUseCase: InitializeHistoryPhasesUseCase,
    private val transaction: DatabaseTransaction
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        transaction.run {
            initializeTranslationsUseCase.executeInternal()
            initializeUserUseCase.executeInternal()
            initializeClassesUseCase.executeInternal()
            val mobsResult = initializeMobsUseCase.executeInternal()
            initializeHistoryPhasesUseCase.executeInternal(mobsResult)
        }
    }
}