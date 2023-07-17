package com.example.jetpackcomposecomponents.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposecomponents.R
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract.UpdateComponentsViewState
import com.example.jetpackcomposecomponents.ui.theme.Black700
import com.example.jetpackcomposecomponents.ui.theme.White200
import com.example.jetpackcomposecomponents.viewmodel.UpdateComponentsViewModel

@Composable
fun UpdateComponents(viewModel: UpdateComponentsViewModel, onCloseCallback: () -> Unit) =
    when (val state = viewModel.viewState.collectAsStateWithLifecycle().value) {
        UpdateComponentsViewState.LoadingState -> LoadingScreen()
        is UpdateComponentsViewState.SuccessState -> SuccessScreen(
            data = state.data,
            onCloseCallback, {
                viewModel.onIntention(
                    UpdateComponentsContract.UpdateComponentsIntention.UpdateData(newData = it)
                )
            }, {
                viewModel.onIntention(
                    UpdateComponentsContract.UpdateComponentsIntention.SaveData
                )
            })
        UpdateComponentsViewState.ErrorState -> ErrorScreen()
    }

@Composable
private fun SuccessScreen(
    data: String,
    onCloseCallback: () -> Unit,
    onDataChangeCallback: (String) -> Unit,
    updateActionClickCallback: () -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
) {
    NavigationBar(onCloseCallback, updateActionClickCallback)

    UpdateJson(data, onDataChangeCallback)
}

@Composable
private fun NavigationBar(
    onCloseCallback: () -> Unit,
    updateActionClickCallback: () -> Unit
) = TopAppBar(
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
    actions = {
        TextButton(
            modifier = Modifier
                .padding(end = 8.dp),
            onClick = { updateActionClickCallback() },
        ) {
            Text(
                text = stringResource(id = R.string.update),
                color = White200
            )
        }
    },
    elevation = 0.dp
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun UpdateJson(data: String, onDataChangeCallback: (String) -> Unit) = Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
        .verticalScroll(rememberScrollState()),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        value = data,
        onValueChange = { newData ->
            onDataChangeCallback(newData)
        },
        maxLines = 50,
        minLines = 25
    )
    Spacer(modifier = Modifier.padding(16.dp))
}