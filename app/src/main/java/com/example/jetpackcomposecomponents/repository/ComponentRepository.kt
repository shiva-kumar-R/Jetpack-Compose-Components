package com.example.jetpackcomposecomponents.repository

import com.example.jetpackcomposecomponents.entity.ComponentDao
import javax.inject.Inject

class ComponentRepository @Inject constructor(
    private val componentDao: ComponentDao
) {
}