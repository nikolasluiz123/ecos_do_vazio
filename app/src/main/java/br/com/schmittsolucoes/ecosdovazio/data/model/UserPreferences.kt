package br.com.schmittsolucoes.ecosdovazio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val selectedCharId: String? = null
)
