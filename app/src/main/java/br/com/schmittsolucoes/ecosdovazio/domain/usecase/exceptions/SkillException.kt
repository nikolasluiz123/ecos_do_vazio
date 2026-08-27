package br.com.schmittsolucoes.ecosdovazio.domain.usecase.exceptions

sealed class SkillException : Exception() {
    class SkillCategoryNotHandled : SkillException()
}