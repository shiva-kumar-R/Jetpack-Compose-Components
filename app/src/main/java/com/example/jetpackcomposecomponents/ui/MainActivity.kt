package com.example.jetpackcomposecomponents.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcomposecomponents.ui.screens.ComponentDetail
import com.example.jetpackcomposecomponents.ui.screens.ComponentsList
import com.example.jetpackcomposecomponents.ui.screens.UpdateComponents
import com.example.jetpackcomposecomponents.ui.theme.JetpackComponentsTheme
import com.example.jetpackcomposecomponents.viewmodel.ComponentDetailViewModel
import com.example.jetpackcomposecomponents.viewmodel.ComponentViewModel
import com.example.jetpackcomposecomponents.viewmodel.UpdateComponentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComponentsTheme {
                ComposeApp()
            }
        }
    }

    @Composable
    fun ComposeApp() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "destination_components_list") {
            composable(route = "destination_components_list") {
                val viewModel: ComponentViewModel = hiltViewModel()
                ComponentsList(viewModel = viewModel, { url ->
                    navController.navigate(route = "destination_component_detail?url=$url")
                }, {
                    navController.navigate(route = "destination_update_components")
                })
            }
            composable(
                route = "destination_component_detail?url={url}",
                arguments = listOf(navArgument(name = "url") {
                    type = NavType.StringType
                })
            ) {
                val viewModel: ComponentDetailViewModel = hiltViewModel()
                ComponentDetail(viewModel = viewModel) {
                    navController.popBackStack()
                }
            }
            composable(
                route = "destination_update_components"
            ) {
                val viewModel: UpdateComponentsViewModel = hiltViewModel()
                UpdateComponents(viewModel = viewModel) {
                    navController.popBackStack(route = "destination_components_list", inclusive = false)
                }
            }
        }
    }
}