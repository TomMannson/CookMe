package com.tommannson.familycooking.infrastructure.api.recipeapi



data class Content(
    val content: List<RecipeDto> = emptyList()
)