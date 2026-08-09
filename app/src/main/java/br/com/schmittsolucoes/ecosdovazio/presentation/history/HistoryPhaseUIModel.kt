package br.com.schmittsolucoes.ecosdovazio.presentation.history

import androidx.annotation.DrawableRes

data class HistoryPhaseUIModel(
    val name: String,
    @DrawableRes val imageResId: Int,
    val isFinished: Boolean,
    val isActual: Boolean
) {
    val isLocked: Boolean get() = !isFinished && !isActual
}
