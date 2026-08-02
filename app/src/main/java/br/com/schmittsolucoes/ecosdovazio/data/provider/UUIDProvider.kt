package br.com.schmittsolucoes.ecosdovazio.data.provider

import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UUIDProvider @Inject constructor(): IdentifierProvider {

    @OptIn(ExperimentalUuidApi::class)
    override fun generate(): String {
        return Uuid.generateV7().toString()
    }
}