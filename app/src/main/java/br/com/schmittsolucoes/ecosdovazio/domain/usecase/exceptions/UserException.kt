package br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions

sealed class UserException : Exception() {
    class UserNotFound : UserException()
}