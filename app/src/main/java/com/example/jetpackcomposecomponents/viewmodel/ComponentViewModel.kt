package com.example.jetpackcomposecomponents.viewmodel

import androidx.lifecycle.ViewModel
import com.example.jetpackcomposecomponents.repository.ComponentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComponentViewModel @Inject constructor(
    private val componentRepository: ComponentRepository
) : ViewModel() {

    companion object {
        private val TAG = ComponentViewModel::class.simpleName
    }

}