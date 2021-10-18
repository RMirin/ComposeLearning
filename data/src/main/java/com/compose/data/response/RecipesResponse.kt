package com.compose.data.response

import com.compose.data.dto.RecipeDto

data class RecipesResponse(
    var recipes: List<RecipeDto>
)