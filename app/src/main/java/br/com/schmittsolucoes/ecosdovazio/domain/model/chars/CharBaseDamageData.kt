package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharBaseDamageData(
    val classCategory: ClassCategory,
    val attributes: List<IdentifiedCharAttribute>,
)
