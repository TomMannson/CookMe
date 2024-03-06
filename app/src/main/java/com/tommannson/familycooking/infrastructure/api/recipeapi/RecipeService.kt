package com.tommannson.familycooking.infrastructure.api.recipeapi;

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


object Api {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.10:8080/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    fun createService() = retrofit.create<RecipeService>()
}

interface RecipeService {

    @POST("v1/recipes")
    suspend fun createRecipe(@Body recipe: RecipeDto): Response<Unit>

    @GET("v1/recipes")
    suspend fun getAllRecipes(): Response<Content>
}