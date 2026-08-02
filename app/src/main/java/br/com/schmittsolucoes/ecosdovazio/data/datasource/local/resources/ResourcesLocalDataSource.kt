package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.resources

interface ResourcesLocalDataSource {
    fun getClassImage(name: String): Int?
}