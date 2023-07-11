package com.example.jetpackcomposecomponents.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jetpackcomposecomponents.entity.Component
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract.ComponentViewState
import com.example.jetpackcomposecomponents.ui.theme.JetpackcomposecomponentsTheme
import com.example.jetpackcomposecomponents.viewmodel.ComponentViewModel
import com.example.jetpackcomposecomponents.R

@Composable
fun ComponentsList(
    viewModel: ComponentViewModel,
    itemClickCallback: (String) -> Unit
) = when (val state = viewModel.viewState.collectAsStateWithLifecycle().value) {
    ComponentViewState.LoadingState -> Unit
    is ComponentViewState.SuccessState -> SuccessScreen(
        componentsList = state.successData,
        itemClickCallback = itemClickCallback,
        updateActionClickCallback = {  }
    )
    ComponentViewState.ErrorState -> Unit
}

@Composable
fun SuccessScreen(
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
fun NavigationBar(updateActionClickCallback: () -> Unit) = TopAppBar(
    modifier = Modifier
        .fillMaxWidth()
        .height(24.dp),
    backgroundColor = Color.Transparent,
    title = { stringResource(id = R.string.navigation_title) },
    actions = {
        Text(
            text = stringResource(id = R.string.update_list),
            modifier = Modifier
                .padding(end = 8.dp)
                .clickable { updateActionClickCallback() },
        )
    },
    elevation = 0.dp
)

@Composable
fun ComponentsContent(
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
fun ComponentItem(component: Component, itemClickCallback: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable {
                itemClickCallback(component.componentUrl)
            }
    ) {
        Column {
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

@Preview("Light mode")
@Preview("Night mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SuccessScreen_Preview() {
    JetpackcomposecomponentsTheme {
        SuccessScreen(emptyList(), {}, {})
    }
}