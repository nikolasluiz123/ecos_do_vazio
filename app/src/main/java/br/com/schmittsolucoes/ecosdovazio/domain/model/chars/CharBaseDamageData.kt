package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

data class CharBaseDamageData(
    val classCategory: ClassCategory,
    val strength: DamageAttributes,
    val dexterity: DamageAttributes,
    val intelligence: DamageAttributes,
) {
    data class DamageAttributes(
        val charValue: Long,
        val classValue: Long,
        val specializationValue: Long?,
    )
}
