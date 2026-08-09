package br.com.schmittsolucoes.ecosdovazio.domain.repository

import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization

interface SpecializationRepository {
    suspend fun save(specializations: List<Specialization>)
}
