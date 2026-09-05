package br.com.schmittsolucoes.ecosdovazio.presentation.skills.extensions

fun String.removeDamageFormula(): String {
    return this.replace(Regex("""\s*\(.*?\)"""), "")
}
