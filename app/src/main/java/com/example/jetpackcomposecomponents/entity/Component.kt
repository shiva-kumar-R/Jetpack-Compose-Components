package com.example.jetpackcomposecomponents.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "component_table")
data class ComponentEntity(
    val components: List<Component>
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

data class Component(
    @SerializedName("id")
    val componentId: String,
    @SerializedName("title")
    val componentTitle: String,
    @SerializedName("description")
    val componentDescription: String,
    @SerializedName("url")
    val componentUrl: String,
)
