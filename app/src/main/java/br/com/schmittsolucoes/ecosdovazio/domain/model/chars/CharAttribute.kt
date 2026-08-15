package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

data class CharAttribute(
    val charValue: Long,
    val classValue: Long,
    val specializationValue: Long?,
) {
    val totalValue: Long
        get() = charValue + classValue + (specializationValue ?: 0L)
}
