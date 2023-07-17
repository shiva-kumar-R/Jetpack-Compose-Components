package com.example.jetpackcomposecomponents.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.jetpackcomposecomponents.ui.theme.White200
import com.example.jetpackcomposecomponents.viewmodel.ComponentViewModel
import kotlinx.coroutines.launch

@Composable
fun ComponentsList(
    viewModel: ComponentViewModel,
    itemClickCallback: (String) -> Unit,
    updateActionNoClickCallback: () -> Unit
) = when (val state = viewModel.viewState.collectAsStateWithLifecycle().value) {
    ComponentViewState.LoadingState -> LoadingScreen()
    is ComponentViewState.SuccessState -> SuccessScreen(
        componentsList = state.successData,
        itemClickCallback = itemClickCallback,
        updateActionYesClickCallback = updateActionNoClickCallback,
        updateActionNoClickCallback = {
            viewModel.onIntention(
                ComponentListContract.ComponentIntention.UpdateList
            )
        }
    )
    ComponentViewState.ErrorState -> ErrorScreen()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SuccessScreen(
    componentsList: List<Component>,
    itemClickCallback: (String) -> Unit,
    updateActionYesClickCallback: () -> Unit,
    updateActionNoClickCallback: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(sheetState = sheetState,
        modifier = Modifier
            .fillMaxSize()
            .background(Black700),
        sheetContent = {
            UpdateListBottomSheet(
                {
                    coroutineScope.launch {
                        if (sheetState.isVisible)
                            sheetState.hide()
                    }
                    updateActionYesClickCallback()
                },
                {
                    coroutineScope.launch {
                        if (sheetState.isVisible)
                            sheetState.hide()
                    }
                    updateActionNoClickCallback()
                }
            )
        }) {
        ComponentsListScreen(
            componentsList = componentsList,
            itemClickCallback = itemClickCallback,
            updateActionClickCallback = {
                coroutineScope.launch {
                    if (sheetState.isVisible)
                        sheetState.hide()
                    else
                        sheetState.show()
                }
            }
        )
    }
}

@Composable
fun UpdateListBottomSheet(
    updateActionYesClickCallback: () -> Unit,
    updateActionNoClickCallback: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.update_list_question),
            modifier = Modifier
                .padding(8.dp),
            style = MaterialTheme.typography.h6
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.yes),
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 2.dp,
                        start = 4.dp,
                        end = 8.dp
                    )
                    .weight(0.2f)
                    .clickable { updateActionYesClickCallback() },
                style = MaterialTheme.typography.button
            )
            Text(
                text = stringResource(id = R.string.no),
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 2.dp,
                        start = 4.dp,
                        end = 4.dp
                    )
                    .weight(0.2f)
                    .clickable { updateActionNoClickCallback() },
                style = MaterialTheme.typography.button
            )
            Spacer(modifier = Modifier.weight(0.6f))
        }
    }
}

@Composable
private fun ComponentsListScreen(
    componentsList: List<Component>,
    itemClickCallback: (String) -> Unit,
    updateActionClickCallback: () -> Unit
) = Column {
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
        TextButton(
            modifier = Modifier
                .padding(end = 8.dp),
            onClick = { updateActionClickCallback() },
        ) {
            Text(
                text = stringResource(id = R.string.update_list),
                color = White200
            )
        }
    },
    elevation = 0.dp
)

@Composable
private fun ComponentsContent(
    componentList: List<Component>,
    itemClickCallback: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(White200),
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
        backgroundColor = Black700,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable {
                itemClickCallback(component.componentUrl)
            }
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.8f)
            ) {
                Text(
                    text = component.componentTitle,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(vertical = 4.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = component.componentDescription,
                        style = MaterialTheme.typography.subtitle2,
                        modifier = Modifier.padding(vertical = 4.dp),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            )
        }
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

@Preview("Light mode")
@Preview("Night mode", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SuccessScreen_Preview() {
    JetpackComponentsTheme {
        SuccessScreen(emptyList(), {}, {}, {})
    }
}