package com.example.videoplayermanager.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import com.example.kalienteapp.ui.theme.BrotherFont
import com.example.videoplayermanager.R

@Composable
fun Player(
    modifier: Modifier = Modifier,
    url: String,
) {
    var isPlaying by remember { mutableStateOf(true) }
    val viewModel: PlayerViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = modifier
    ) {
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(5),
                )
                .aspectRatio(16 / 9f),
            factory = { context ->
                PlayerView(context).also {
                    it.player = viewModel.player
                    it.player?.setMediaItem(MediaItem.fromUri(url))
                }
            },
            update = {
                when (lifecycle) {
                     Lifecycle.Event.ON_PAUSE -> {
                         it.onPause()
                         it.player?.pause()
                     }

                     Lifecycle.Event.ON_RESUME -> {
                         it.onResume()
                         it.player?.play()
                     }

                    Lifecycle.Event.ON_DESTROY -> {
                        it.player?.pause()
                    }

                     else -> Unit
                 }
            },
        )
        Image(
            painter = painterResource(id = R.drawable.ic_kaliente),
            contentDescription = "Cover Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.Center) // Adjust alignment as needed
                .size(200.dp) // Adjust size
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = state.songTitle,
            style = BrotherFont,
            fontWeight = FontWeight.Thin,
            fontSize = 15.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))
        // Playback Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                viewModel.rewind()
            }) {
                Icon(
                    painterResource(id = R.drawable.rewind),
                    modifier = Modifier.size(40.dp),
                    contentDescription = "Previous",
                )
            }

            IconButton(
                onClick = {
                    if (isPlaying) {
                        viewModel.player.pause()
                    } else {
                        viewModel.player.playWhenReady = true
                    }
                    isPlaying = !isPlaying // Toggle playback state
                }
            ) {
                Icon(
                    if (isPlaying) Icons.Default.AddCircle else Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    modifier = Modifier.size(56.dp)
                )
            }

            IconButton(onClick = {
                viewModel.forward()
            }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Next",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
