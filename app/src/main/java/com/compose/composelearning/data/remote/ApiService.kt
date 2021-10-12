package com.compose.composelearning.data.remote

import com.compose.composelearning.data.response.RecipesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("recipes/random")
    suspend fun getRandom(
        @Query("number") number: Int,
        @Query("apiKey") apiKey: String
    ): RecipesResponse
}