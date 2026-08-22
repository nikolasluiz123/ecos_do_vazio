package br.com.schmittsolucoes.ecosdovazio.core.injection

import br.com.schmittsolucoes.ecosdovazio.core.formatters.LocalizableNumberFormatter
import br.com.schmittsolucoes.ecosdovazio.core.formatters.NumberFormatter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FormatterModule {

    @Binds
    @Singleton
    abstract fun bindNumberFormatter(impl: LocalizableNumberFormatter): NumberFormatter
}
