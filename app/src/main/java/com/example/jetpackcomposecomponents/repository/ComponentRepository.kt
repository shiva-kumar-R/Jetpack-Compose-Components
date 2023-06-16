package com.example.jetpackcomposecomponents.repository

import com.example.jetpackcomposecomponents.entity.Component
import com.example.jetpackcomposecomponents.entity.ComponentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ComponentRepository @Inject constructor(
    private val componentDao: ComponentDao
) {

    val allComponents: Flow<List<Component>>
        get() = componentDao.getAllComponents().flowOn(Dispatchers.IO)

    suspend fun updateComponents(component: List<Component>) =
        componentDao.updateComponents(component)

}