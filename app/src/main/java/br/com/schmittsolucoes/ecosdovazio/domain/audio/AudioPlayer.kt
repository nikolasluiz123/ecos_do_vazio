package br.com.schmittsolucoes.ecosdovazio.domain.audio

import androidx.annotation.RawRes

interface AudioPlayer {
    suspend fun playSound(@RawRes soundResId: Int)
}
