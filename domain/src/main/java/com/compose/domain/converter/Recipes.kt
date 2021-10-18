package com.compose.domain.converter

import com.compose.data.dto.RecipeDto
import com.compose.data.response.RecipesResponse
import com.compose.domain.entities.RecipeModel
import com.compose.domain.entities.RecipesModel

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
