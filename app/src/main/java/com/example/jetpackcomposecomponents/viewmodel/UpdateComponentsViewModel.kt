package com.example.jetpackcomposecomponents.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract.UpdateComponentsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UpdateComponentsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _viewState = MutableStateFlow<UpdateComponentsViewState>(
        UpdateComponentsViewState.LoadingState
    )

    val viewState: StateFlow<UpdateComponentsViewState> get() = _viewState.asStateFlow()

    companion object {
        private val TAG = UpdateComponentsViewModel::class.simpleName
    }

    private val componentUrl: String = savedStateHandle.get<String>("url").orEmpty()

    init {
        if (componentUrl.isNotEmpty()) {
            updateViewState {
                UpdateComponentsViewState.SuccessState(componentUrl)
            }
        } else {
            updateViewState {
                UpdateComponentsViewState.ErrorState
            }
        }
    }

    fun onIntention(intention: UpdateComponentsContract.UpdateComponentsIntention): Any? =
        when (intention) {
            is UpdateComponentsContract.UpdateComponentsIntention.UpdateData -> getSuccessData()?.let {
                UpdateComponentsViewState.SuccessState(data = it + intention.newData)
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