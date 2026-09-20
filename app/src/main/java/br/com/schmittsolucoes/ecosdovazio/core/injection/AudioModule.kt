package br.com.schmittsolucoes.ecosdovazio.core.injection

import br.com.schmittsolucoes.ecosdovazio.data.audio.AndroidMediaPlayer
import br.com.schmittsolucoes.ecosdovazio.data.audio.SkillMediaPlayer
import br.com.schmittsolucoes.ecosdovazio.domain.audio.AudioPlayer
import br.com.schmittsolucoes.ecosdovazio.domain.audio.SkillAudioPlayer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AudioModule {

    @Binds
    @Singleton
    abstract fun bindAudioPlayer(
        impl: AndroidMediaPlayer
    ): AudioPlayer

    @Binds
    @Singleton
    abstract fun bindSkillAudioPlayer(
        impl: SkillMediaPlayer
    ): SkillAudioPlayer
}
