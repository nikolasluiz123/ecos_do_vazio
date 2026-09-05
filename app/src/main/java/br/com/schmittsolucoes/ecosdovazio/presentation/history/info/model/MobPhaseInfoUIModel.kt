package br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model

import androidx.annotation.DrawableRes

data class MobPhaseInfoUIModel(
    val mobId: String,
    val mobName: String,
    val mobDescription: String,
    @DrawableRes val mobProfileImage: Int,
    val mobCount: String,
)
