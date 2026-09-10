package br.com.schmittsolucoes.ecosdovazio.presentation.chars.model

data class CharLevelInfoUIModel(
    val level: String = "1",
    val currentExperience: String = "0",
    val nextLevelExperience: String = "0",
    val progress: Float = 0f
)
