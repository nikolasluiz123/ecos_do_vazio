package br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions

sealed class CharException : Exception() {
    class ClassSelectionRequired : CharException()
    class NameRequired : CharException()
    data class DuplicatedName(val name: String) : CharException()
    data class NameTooLong(val maxLength: Int): CharException()
}