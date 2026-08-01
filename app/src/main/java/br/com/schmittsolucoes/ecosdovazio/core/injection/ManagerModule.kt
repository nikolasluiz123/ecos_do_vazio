package br.com.schmittsolucoes.ecosdovazio.core.injection

import br.com.schmittsolucoes.ecosdovazio.data.manager.LoadingManagerImpl
import br.com.schmittsolucoes.ecosdovazio.data.manager.SnackbarManagerImpl
import br.com.schmittsolucoes.ecosdovazio.domain.manager.LoadingManager
import br.com.schmittsolucoes.ecosdovazio.domain.manager.SnackbarManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindLoadingManager(impl: LoadingManagerImpl): LoadingManager

    @Binds
    @Singleton
    abstract fun bindSnackbarManager(impl: SnackbarManagerImpl): SnackbarManager
}
