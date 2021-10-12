package com.compose.composelearning.data.dto

import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("image") val image: String = "",
    @SerializedName("summary") val summary: String = ""
)