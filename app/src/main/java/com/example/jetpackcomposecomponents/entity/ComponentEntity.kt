package com.example.jetpackcomposecomponents.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "component_table")
class ComponentEntity(
    val components: List<Component>
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}

