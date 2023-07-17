package com.example.jetpackcomposecomponents.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecomponents.repository.ComponentRepository
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract.UpdateComponentsViewState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateComponentsViewModel @Inject constructor(
    private val componentRepository: ComponentRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow<UpdateComponentsViewState>(
        UpdateComponentsViewState.LoadingState
    )

    val viewState: StateFlow<UpdateComponentsViewState> get() = _viewState.asStateFlow()

    companion object {
        private val TAG = UpdateComponentsViewModel::class.simpleName
    }

    init {
        getAllComponents()
    }

    private fun getAllComponents() {
        flow {
            emit(componentRepository.getComponents())
        }.onEach { data ->
            updateViewState {
                UpdateComponentsViewState.SuccessState(
                    data = data.toString()
                )
            }
        }.catch {
            updateViewState {
                UpdateComponentsViewState.ErrorState
            }
        }.launchIn(viewModelScope)
    }

    fun onIntention(intention: UpdateComponentsContract.UpdateComponentsIntention): Any? =
        when (intention) {
            is UpdateComponentsContract.UpdateComponentsIntention.UpdateData -> getSuccessData()?.let {
                UpdateComponentsViewState.SuccessState(data = it + intention.newData)
            }
            UpdateComponentsContract.UpdateComponentsIntention.SaveData -> updateComponents()
        }

    private fun updateComponents() {
        viewModelScope.launch {

        }
    }

    private fun getSuccessData() = viewState.value.run {
        if (this is UpdateComponentsViewState.SuccessState) {
            data
        } else null
    }

    private fun updateViewState(stateModifier: (UpdateComponentsViewState) -> UpdateComponentsViewState) {
        _viewState.update(stateModifier)
    }
}