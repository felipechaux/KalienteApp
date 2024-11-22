package com.example.videoplayermanager.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.media3.common.Player
import com.example.videoplayermanager.utils.Constants.REWIND_FORWARD_SECONDS
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val player: Player,
    @ApplicationContext context: Context,
) : ViewModel() {

    init {
        player.prepare()
        player.play()
    }

    fun forward() {
        val currentPosition = player.currentPosition
        val newPosition = currentPosition + REWIND_FORWARD_SECONDS
        player.seekTo(newPosition)
    }

    fun rewind(){
        val currentPosition = player.currentPosition
        val newPosition = (currentPosition - REWIND_FORWARD_SECONDS).coerceAtLeast(0)
        player.seekTo(newPosition)
    }
}
