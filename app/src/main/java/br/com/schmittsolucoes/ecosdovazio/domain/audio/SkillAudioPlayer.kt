package br.com.schmittsolucoes.ecosdovazio.domain.audio

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier

interface SkillAudioPlayer {
    suspend fun playSkillSound(identifier: TranslationIdentifier)
}
