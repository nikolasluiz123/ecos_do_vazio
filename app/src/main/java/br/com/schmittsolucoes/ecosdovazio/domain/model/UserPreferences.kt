package br.com.schmittsolucoes.ecosdovazio.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val selectedCharId: String? = null
)