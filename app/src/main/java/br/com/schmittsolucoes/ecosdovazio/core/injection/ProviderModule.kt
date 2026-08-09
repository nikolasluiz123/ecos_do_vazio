package br.com.schmittsolucoes.ecosdovazio.core.injection

import br.com.schmittsolucoes.ecosdovazio.data.provider.AndroidResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.data.provider.LocaleLanguageProvider
import br.com.schmittsolucoes.ecosdovazio.data.provider.UUIDProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    abstract fun bindIdentifierProvider(impl: UUIDProvider): IdentifierProvider

    @Binds
    @Singleton
    abstract fun bindLanguageProvider(impl: LocaleLanguageProvider): LanguageProvider

    @Binds
    abstract fun bindResourcesProvider(impl: AndroidResourcesProvider): ResourcesProvider
}
