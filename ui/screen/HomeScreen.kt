package com.example.kalienteapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.kalienteapp.utils.Constants.VIDEO_URL
import com.example.videoplayermanager.presentation.Player

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val gradientColors = listOf(
        Color(0xFF001F3F), // Dark Blue
        Color(0xFF003366)  // Deeper Dark Blue
    )
    val gradientBrush = Brush.verticalGradient(gradientColors)
    Box(
        modifier
            .fillMaxSize()
            .background(gradientBrush)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Player(
                modifier = Modifier.fillMaxWidth(),
                url = VIDEO_URL
            )
        }
    }
}