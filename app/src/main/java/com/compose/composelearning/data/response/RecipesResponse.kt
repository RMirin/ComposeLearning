package com.compose.composelearning.data.response

import com.compose.composelearning.data.dto.RecipeDto

data class RecipesResponse(
    var recipes: List<RecipeDto>
)