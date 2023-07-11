package com.example.jetpackcomposecomponents.entity

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Component::class], version = 1, exportSchema = true)
@TypeConverters(ComponentTypeConverter::class)
abstract class ComponentDatabase: RoomDatabase() {
    abstract fun getComponentDao(): ComponentDao
}