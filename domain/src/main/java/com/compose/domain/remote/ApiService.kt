package com.compose.domain.remote

import com.compose.data.response.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random")
    suspend fun getRandom(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): RecipesResponse
}