package com.example.jetpackcomposecomponents.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposecomponents.entity.Component
import com.example.jetpackcomposecomponents.repository.ComponentRepository
import com.example.jetpackcomposecomponents.ui.contract.ComponentListContract
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract
import com.example.jetpackcomposecomponents.ui.contract.UpdateComponentsContract.UpdateComponentsViewState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
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
        flow {
            emit(componentRepository.getComponents())
        }.onEach { data ->
            val listComponent = object : TypeToken<List<Component>>() {}.type
            val response = Gson().toJson(data, listComponent)
            updateViewState {
                UpdateComponentsViewState.SuccessState(
                    data = formatJsonString(response)
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
            is UpdateComponentsContract.UpdateComponentsIntention.UpdateData -> updateViewState {
                UpdateComponentsViewState.SuccessState(intention.newData)
            }
            UpdateComponentsContract.UpdateComponentsIntention.SaveData -> updateComponents()
        }

    private fun updateComponents() {
        val data = getSuccessData()
        val listComponent = object : TypeToken<List<Component>>() {}.type
        val response = Gson().fromJson<List<Component>>(data, listComponent)
        flow {
            emit(response?.let { componentRepository.updateComponents(response) })
        }.onStart {
            updateViewState {
                UpdateComponentsViewState.LoadingState
            }
        }.onCompletion {
            updateViewState {
                UpdateComponentsViewState.UpdateListSuccessState
            }
        }.catch {
            updateViewState {
                UpdateComponentsViewState.ErrorState
            }
        }.launchIn(viewModelScope)
    }

    private fun getSuccessData() = viewState.value.run {
        if (this is UpdateComponentsViewState.SuccessState) {
            data
        } else null
    }

    private fun updateViewState(stateModifier: (UpdateComponentsViewState) -> UpdateComponentsViewState) {
        _viewState.update(stateModifier)
    }

    private fun formatJsonString(text: String): String {
        val json = StringBuilder()
        var indentString = ""
        for (element in text) {
            when (element) {
                '{', '[' -> {
                    json.append(
                        """

                        $indentString$element
                        
                        """.trimIndent()
                    )
                    indentString += "\t"
                    json.append(indentString)
                }
                '}', ']' -> {
                    indentString = indentString.replaceFirst("\t".toRegex(), "")
                    json.append(
                        """
                        
                        $indentString$element
                        """.trimIndent()
                    )
                }
                ',' -> json.append(
                    """
                    $element
                    $indentString
                    """.trimIndent()
                )
                else -> json.append(element)
            }
        }
        return json.toString()
    }
}