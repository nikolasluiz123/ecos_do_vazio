package br.com.schmittsolucoes.ecosdovazio.presentation.chars.model

data class CharLevelInfoUIModel(
    val level: String = "",
    val currentExperience: String = "",
    val nextLevelExperience: String = "",
    val progress: Float = 0f
)
