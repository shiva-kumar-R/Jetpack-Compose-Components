package com.example.jetpackcomposecomponents.entity

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Component::class], version = 1)
abstract class ComponentDatabase: RoomDatabase() {
    abstract fun getComponentDao(): ComponentDao
}