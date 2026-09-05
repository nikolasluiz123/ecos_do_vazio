package br.com.schmittsolucoes.ecosdovazio.presentation.components.models

import androidx.annotation.DrawableRes

data class SelectionItemUIModel(
    val id: String,
    val name: String,
    val description: String,
    @param:DrawableRes val presentationDrawableId: Int,
)