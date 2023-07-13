package com.example.jetpackcomposecomponents.repository

import android.content.Context
import android.util.Log
import com.example.jetpackcomposecomponents.entity.Component
import com.example.jetpackcomposecomponents.entity.ComponentDao
import com.example.jetpackcomposecomponents.entity.ComponentEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ComponentRepository @Inject constructor(
    @ApplicationContext private val context: Context,
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

    fun getComponentsFromJson(): List<Component>? = try {
        val jsonString = context.assets.open("components.json")
            .bufferedReader()
            .use { it.readText() }
        val listComponent = object : TypeToken<List<Component>>() {}.type
        val response = Gson().fromJson<List<Component>>(jsonString, listComponent)
        response
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