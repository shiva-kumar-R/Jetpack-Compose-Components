package com.example.jetpackcomposecomponents.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecomponents.repository.ComponentRepository
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract.ComponentViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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

    init {
        getAllComponents()
    }

    private fun updateComponents() {
        val components = getComponentsFromJson()
        flow {
            emit(components?.let { componentRepository.updateComponents(it) })
        }.onCompletion {
            getAllComponents()
        }.catch {
            updateViewState {
                ComponentViewState.ErrorState
            }
        }.launchIn(viewModelScope)
    }

    private fun getComponentsFromJson() = try {
        componentRepository.getComponentsFromJson()
    } catch (e: Exception) {
        null
    }

    private fun getAllComponents() {
        flow {
            emit(componentRepository.getComponents())
        }.onEach { data ->
            updateViewState {
                ComponentViewState.SuccessState(
                    successData = data.orEmpty()
                )
            }
        }.catch {
            updateViewState {
                ComponentViewState.ErrorState
            }
        }.launchIn(viewModelScope)
    }

    fun onIntention(intention: ComponentListContract.ComponentIntention): Any =
        when (intention) {
            ComponentListContract.ComponentIntention.UpdateList -> updateComponents()
        }

    private fun updateViewState(stateModifier: (ComponentViewState) -> ComponentViewState) {
        _viewState.update(stateModifier)
    }
}