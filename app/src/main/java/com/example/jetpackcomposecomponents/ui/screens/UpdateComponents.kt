package com.example.jetpackcomposecomponents.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract.UpdateComponentsViewState
import com.example.jetpackcomposecomponents.ui.theme.Black700
import com.example.jetpackcomposecomponents.viewmodel.UpdateComponentsViewModel

@Composable
fun UpdateComponents(viewModel: UpdateComponentsViewModel, onCloseCallback: () -> Unit) =
    when (val state = viewModel.viewState.collectAsStateWithLifecycle().value) {
        UpdateComponentsViewState.LoadingState -> LoadingScreen()
        is UpdateComponentsViewState.SuccessState -> SuccessScreen(
            data = state.data,
            onCloseCallback
        ) {
            viewModel.onIntention(
                UpdateComponentsContract.UpdateComponentsIntention.UpdateData(newData = it)
            )
        }
        UpdateComponentsViewState.ErrorState -> ErrorScreen()
    }

@Composable
private fun SuccessScreen(
    data: String,
    onCloseCallback: () -> Unit,
    onDataChangeCallback: (String) -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
) {
    NavigationBar(onCloseCallback)

    UpdateJson(data, onDataChangeCallback)
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
private fun UpdateJson(data: String, onDataChangeCallback: (String) -> Unit) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        value = data,
        onValueChange = { newData ->
            onDataChangeCallback(newData)
        }
    )
}