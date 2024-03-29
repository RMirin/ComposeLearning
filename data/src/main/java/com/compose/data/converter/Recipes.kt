package com.compose.data.converter

import com.compose.data.dto.RecipeDto
import com.compose.domain.model.RecipeModel
import com.compose.domain.model.RecipesModel
import com.compose.data.response.RecipesResponse

fun RecipesResponse.toModel() = RecipesModel().also {
    it.recipes = recipeDtoListToRecipeModelList(recipes)
}

fun recipeDtoListToRecipeModelList(recipeDtoList: List<RecipeDto>): List<RecipeModel> {
    val recipeModelList = mutableListOf<RecipeModel>()
    for (recipeDto in recipeDtoList) {
        recipeModelList.add(recipeDto.toModel())
    }
    return recipeModelList
}

fun RecipeDto.toModel() = RecipeModel().also {
    it.id = id
    it.summary = summary
    it.title = title
}
