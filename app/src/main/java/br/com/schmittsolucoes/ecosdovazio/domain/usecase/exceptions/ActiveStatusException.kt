package br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions

sealed class ActiveStatusException : Exception() {
    class StatusNotHandled : ActiveStatusException()
}