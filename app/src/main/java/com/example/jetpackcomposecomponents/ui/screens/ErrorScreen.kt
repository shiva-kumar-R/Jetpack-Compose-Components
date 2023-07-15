package com.example.jetpackcomposecomponents.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.jetpackcomposecomponents.R


@Composable
internal fun ErrorScreen() {
    Toast.makeText(LocalContext.current, R.string.error_message, Toast.LENGTH_LONG).show()
}