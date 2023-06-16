package com.example.jetpackcomposecomponents.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecomponents.repository.ComponentRepository
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract.ComponentViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComponentViewModel @Inject constructor(
    private val componentRepository: ComponentRepository
) : ViewModel() {

    companion object {
        private val TAG = ComponentViewModel::class.simpleName
    }

    private val _viewState = MutableStateFlow<ComponentViewState>(ComponentViewState.LoadingState)

    val viewState: StateFlow<ComponentViewState> get() = _viewState.asStateFlow()

    suspend fun updateComponents() {
        viewModelScope.launch {
            componentRepository.updateComponents(getComponentSuccessState().orEmpty())
        }
    }

    suspend fun getAllComponents() {
        viewModelScope.launch {
            componentRepository.allComponents.collect { data ->
                updateViewState {
                    ComponentViewState.SuccessState(
                        successData = data
                    )
                }
            }
        }
    }

    private fun getComponentSuccessState() = viewState.value.run {
        if(this is ComponentViewState.SuccessState) {
            successData
        } else null
    }

    private fun updateViewState(stateModifier: (ComponentViewState) -> ComponentViewState) {
        _viewState.update(stateModifier)
    }
}