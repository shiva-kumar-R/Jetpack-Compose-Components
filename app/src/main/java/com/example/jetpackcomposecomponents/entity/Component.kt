package com.example.jetpackcomposecomponents.entity

import com.google.gson.annotations.SerializedName

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
