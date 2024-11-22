package com.example.videoplayermanager.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.example.network.base.ApiResource
import com.example.videoplayermanager.usecases.GetPlayerStatusUseCase
import com.example.videoplayermanager.utils.Constants.MINUTES
import com.example.videoplayermanager.utils.Constants.REWIND_FORWARD_SECONDS
import com.example.videoplayermanager.utils.Constants.SECONDS
import com.example.videoplayermanager.utils.Constants.TIME_MS
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    val player: Player,
    private val getPlayStatusUseCase: GetPlayerStatusUseCase,
    @ApplicationContext context: Context,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state
    private var isPlayable: Boolean = true

    init {
        player.prepare()
        player.play()
        viewModelScope.launch {
            while (isPlayable) {
                getPlayerStatus()
                delay(MINUTES * SECONDS.toLong() * TIME_MS.toLong())
            }
        }
    }

    fun stopDelay() {
        isPlayable = false
    }

    private fun getPlayerStatus() {
        _state.value = HomeUiState(loading = true)
        viewModelScope.launch {
            val response = getPlayStatusUseCase.invoke()
            when (response.status) {
                ApiResource.Status.SUCCESS -> {
                    response.data?.let { nasaItems ->
                        nasaItems.icestats.source.filter { it.mount == "/orl-kaliente" }.forEach { it ->
                            println("title ${it.title}")
                            _state.value = HomeUiState(songTitle = it.title)
                        }
                    }
                }

                ApiResource.Status.ERROR -> {
                    println("error")
                }
            }
        }
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

    data class HomeUiState(
        val loading: Boolean = false,
        val songTitle: String = "",
    )
}
