package com.example.jetpackcomposecomponents.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "component_table")
data class Component(
    @ColumnInfo(name = "title")
    val componentTitle: String,
    @ColumnInfo(name = "description")
    val componentDescription: String,
    @ColumnInfo(name = "urlLink")
    val componentUrl: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
