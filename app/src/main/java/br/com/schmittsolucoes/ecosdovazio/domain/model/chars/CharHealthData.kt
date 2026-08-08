package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharHealthData(
    val classCategory: ClassCategory,
    val charVitality: Long,
    val classIncrementVitality: Long,
    val specializationIncrementVitality: Long?,
)
