package com.example.jetpackcomposecomponents.repository

import com.example.jetpackcomposecomponents.entity.Component
import com.example.jetpackcomposecomponents.entity.ComponentDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ComponentRepository @Inject constructor(
    private val componentDao: ComponentDao
) {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    fun updateComponents(component: List<Component>) =
        coroutineScope.launch(Dispatchers.IO) {
            componentDao.updateComponents(component)
        }

    fun getAllComponents(components: List<Component>) =
        coroutineScope.launch(Dispatchers.IO) {
            componentDao.getAllComponents()
        }
}