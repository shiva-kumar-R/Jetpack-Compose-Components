package com.example.jetpackcomposecomponents.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposecomponents.ui.contract.ComponentDetailContract.ComponentDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ComponentDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableStateFlow<ComponentDetailViewState>(
        ComponentDetailViewState.LoadingState
    )

    val viewState: StateFlow<ComponentDetailViewState> get() = _viewState.asStateFlow()

    companion object {
        private val TAG = ComponentDetailViewModel::class.simpleName
    }

    private val componentUrl: String = savedStateHandle.get<String>("url").orEmpty()

    init {
        if (componentUrl.isNotEmpty()) {
            updateViewState {
                ComponentDetailViewState.SuccessState(componentUrl)
            }
        } else {
            updateViewState {
                ComponentDetailViewState.ErrorState
            }
        }
    }

    private fun updateViewState(stateModifier: (ComponentDetailViewState) -> ComponentDetailViewState) {
        _viewState.update(stateModifier)
    }
}