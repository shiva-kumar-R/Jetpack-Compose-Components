package com.example.jetpackcomposecomponents.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ComponentTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun componentToString(components: List<Component>): String {
        return gson.toJson(components)
    }

    @TypeConverter
    fun stringToComponent(recipeString: String): List<Component> {
        val objectType = object : TypeToken<List<Component>>() {}.type
        return gson.fromJson(recipeString, objectType)
    }

}