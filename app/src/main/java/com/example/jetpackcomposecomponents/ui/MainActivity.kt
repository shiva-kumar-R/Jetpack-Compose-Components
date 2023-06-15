package com.example.jetpackcomposecomponents.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposecomponents.ui.theme.JetpackcomposecomponentsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackcomposecomponentsTheme {
                ComposeApp()
            }
        }
    }

    @Composable
    fun ComposeApp() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "destination_components_list") {
            composable(route = "destination_components_list") {

            }
        }
    }
}