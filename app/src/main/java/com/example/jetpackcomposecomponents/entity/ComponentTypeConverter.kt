package com.example.jetpackcomposecomponents.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ComponentTypeConverter {

    val gson = Gson()

    @TypeConverter
    fun componentToString(component: Component): String {
        return gson.toJson(component)
    }

    @TypeConverter
    fun stringToComponent(recipeString: String): Component {
        val objectType = object : TypeToken<Component>() {}.type
        return gson.fromJson(recipeString, objectType)
    }

}