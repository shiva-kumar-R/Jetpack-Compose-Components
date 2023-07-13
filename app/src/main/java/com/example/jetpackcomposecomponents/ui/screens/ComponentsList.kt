package com.example.jetpackcomposecomponents.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposecomponents.R
import com.example.jetpackcomposecomponents.entity.Component
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract.ComponentViewState
import com.example.jetpackcomposecomponents.ui.theme.Black700
import com.example.jetpackcomposecomponents.ui.theme.JetpackComponentsTheme
import com.example.jetpackcomposecomponents.viewmodel.ComponentViewModel

@Composable
fun ComponentsList(
    viewModel: ComponentViewModel,
    itemClickCallback: (String) -> Unit
) = when (val state = viewModel.viewState.collectAsStateWithLifecycle().value) {
    ComponentViewState.LoadingState -> LoadingScreen()
    is ComponentViewState.SuccessState -> SuccessScreen(
        componentsList = state.successData,
        itemClickCallback = itemClickCallback,
        updateActionClickCallback = {
            viewModel.onIntention(
                ComponentListContract.ComponentIntention.UpdateList
            )
        }
    )
    ComponentViewState.ErrorState -> ErrorScreen()
}

@Composable
internal fun LoadingScreen() = Box(modifier = Modifier.fillMaxSize()) {
    CircularProgressIndicator(
        modifier = Modifier.align(Alignment.Center),
        color = Black700
    )
}

@Composable
private fun SuccessScreen(
    componentsList: List<Component>,
    itemClickCallback: (String) -> Unit,
    updateActionClickCallback: () -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
) {
    NavigationBar(updateActionClickCallback)

    ComponentsContent(componentsList, itemClickCallback)
}

@Composable
private fun NavigationBar(updateActionClickCallback: () -> Unit) = TopAppBar(
    modifier = Modifier
        .fillMaxWidth(),
    title = {
        Text(
            text = stringResource(id = R.string.navigation_title),
            style = MaterialTheme.typography.h5
        )
    },
    actions = {
        Text(
            text = stringResource(id = R.string.update_list),
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable { updateActionClickCallback() },
            style = MaterialTheme.typography.button
        )
    },
    elevation = 0.dp
)

@Composable
private fun ComponentsContent(
    componentList: List<Component>,
    itemClickCallback: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 4.dp,
            bottom = 16.dp
        )
    ) {
        items(componentList) { data ->
            ComponentItem(data, itemClickCallback)
        }
    }
}

@Composable
private fun ComponentItem(component: Component, itemClickCallback: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                itemClickCallback(component.componentUrl)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = component.componentTitle,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(vertical = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = component.componentDescription,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(vertical = 4.dp),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
internal fun ErrorScreen() {
    Toast.makeText(LocalContext.current, R.string.error_message, Toast.LENGTH_LONG).show()
}

@Preview("Light mode")
@Preview("Night mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SuccessScreen_Preview() {
    JetpackComponentsTheme {
        SuccessScreen(emptyList(), {}, {})
    }
}

@Preview("Light mode")
@Preview("Night mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoadingScreen_Preview() {
    JetpackComponentsTheme {
        LoadingScreen()
    }
}