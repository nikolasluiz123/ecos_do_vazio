package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharCriticalData(
    val classCategory: ClassCategory,
    val charDexterity: Long,
    val classIncrementDexterity: Long,
    val specializationIncrementDexterity: Long?
)
