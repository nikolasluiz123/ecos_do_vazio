package br.com.schmittsolucoes.ecosdovazio.data.audio

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import br.com.schmittsolucoes.ecosdovazio.domain.audio.AudioPlayer
import br.com.schmittsolucoes.ecosdovazio.presentation.DEBUG_PROCESS_TAG
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidMediaPlayer @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AudioPlayer {

    override suspend fun playSound(soundResId: Int) {
        suspendCancellableCoroutine { continuation ->
            var mediaPlayer: MediaPlayer? = null

            try {
                mediaPlayer = MediaPlayer.create(context, soundResId)

                if (mediaPlayer == null) {
                    continuation.resume(Unit)
                    return@suspendCancellableCoroutine
                }

                mediaPlayer.setOnCompletionListener {
                    it.release()

                    if (continuation.isActive) {
                        continuation.resume(Unit)
                    }
                }

                mediaPlayer.setOnErrorListener { mp, whatCode, extraCode ->
                    Log.e(DEBUG_PROCESS_TAG, "Media Player Error. What Code: $whatCode, Extra Code: $extraCode")

                    mp.release()

                    if (continuation.isActive) {
                        continuation.resume(Unit)
                    }

                    true
                }

                continuation.invokeOnCancellation {
                    mediaPlayer.release()
                }

                mediaPlayer.start()
            } catch (e: Exception) {
                Log.e(DEBUG_PROCESS_TAG, e.message, e)

                mediaPlayer?.release()

                if (continuation.isActive) {
                    continuation.resume(Unit)
                }
            }
        }
    }
}
