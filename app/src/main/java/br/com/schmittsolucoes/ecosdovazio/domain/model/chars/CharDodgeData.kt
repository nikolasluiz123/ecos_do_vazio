package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharDodgeData(
    val classCategory: ClassCategory,
    val charAgility: Long,
    val classIncrementAgility: Long,
    val specializationIncrementAgility: Long?
)
