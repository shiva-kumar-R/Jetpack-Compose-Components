package com.example.jetpackcomposecomponents.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposecomponents.R
import com.example.jetpackcomposecomponents.ui.contract.ComponentDetailContract.ComponentDetailViewState
import com.example.jetpackcomposecomponents.ui.theme.Black700
import com.example.jetpackcomposecomponents.viewmodel.ComponentDetailViewModel

@Composable
fun ComponentDetail(viewModel: ComponentDetailViewModel, onCloseCallback: () -> Unit) =
    when (val state = viewModel.viewState.collectAsStateWithLifecycle().value) {
        ComponentDetailViewState.LoadingState -> LoadingScreen()
        is ComponentDetailViewState.SuccessState -> SuccessScreen(
            url = state.url,
            onCloseCallback
        )
        ComponentDetailViewState.ErrorState -> ErrorScreen()
    }

@Composable
private fun SuccessScreen(url: String, onCloseCallback: () -> Unit) = Column(
    modifier = Modifier
        .fillMaxSize()
) {
    NavigationBar(onCloseCallback)

    WebViewScreen(url)
}

@Composable
private fun NavigationBar(onCloseCallback: () -> Unit) = TopAppBar(
    modifier = Modifier
        .fillMaxWidth(),
    title = { },
    navigationIcon = {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "",
            modifier = Modifier
                .padding(10.dp)
                .clickable { onCloseCallback() },
            tint = Black700
        )
    },
    elevation = 0.dp
)

@Composable
private fun WebViewScreen(url: String) = AndroidView(
    modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
    factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })