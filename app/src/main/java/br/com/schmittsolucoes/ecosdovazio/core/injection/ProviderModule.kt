package br.com.schmittsolucoes.ecosdovazio.core.injection

import br.com.schmittsolucoes.ecosdovazio.data.provider.LocaleLanguageProvider
import br.com.schmittsolucoes.ecosdovazio.data.provider.UUIDProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProviderModule {

    @Binds
    abstract fun bindIdentifierProvider(impl: UUIDProvider): IdentifierProvider

    @Binds
    abstract fun bindLanguageProvider(impl: LocaleLanguageProvider): LanguageProvider
}
