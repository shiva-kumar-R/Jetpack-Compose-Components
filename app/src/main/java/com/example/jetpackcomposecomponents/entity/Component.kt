package com.example.jetpackcomposecomponents.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "component_table")
data class ComponentEntity(
    val components: List<Component>
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

data class Component(
    val componentId: String,
    val componentTitle: String,
    val componentDescription: String,
    val componentUrl: String,
)
