package com.example.jetpackcomposecomponents.repository

import android.util.Log
import com.example.jetpackcomposecomponents.entity.Component
import com.example.jetpackcomposecomponents.entity.ComponentDao
import com.example.jetpackcomposecomponents.entity.ComponentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ComponentRepository @Inject constructor(
    private val componentDao: ComponentDao
) {

    var allComponents: List<Component>? = null

    suspend fun getComponents(): List<Component>? = try {
        val componentEntity = componentDao.getAllComponents()
        componentEntity?.let {
            allComponents = it.components
        }
        allComponents
    } catch (e: Exception) {
        Log.w(TAG, e)
        null
    }

    suspend fun updateComponents(components: List<Component>) = try {
        deleteComponents()
        invalidateCache()
        insertComponents(components)
    } catch (e: Exception) {
        Log.w(TAG, e)
        null
    }

    private suspend fun insertComponents(components: List<Component>) {
        componentDao.insertAllComponents(ComponentEntity(components))
    }

    private suspend fun deleteComponents() {
        allComponents?.let { ComponentEntity(it) }?.let {
            componentDao.deleteAllComponents(it)
        }
    }

    private fun invalidateCache() {
        allComponents = null
    }

    companion object {
        private val TAG = ComponentRepository::class.simpleName
    }
}