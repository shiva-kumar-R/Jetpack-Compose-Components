package com.example.jetpackcomposecomponents.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jetpackcomposecomponents.ui.theme.Black700


@Composable
internal fun LoadingScreen() = Box(modifier = Modifier.fillMaxSize()) {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
        color = Black700
    )
}