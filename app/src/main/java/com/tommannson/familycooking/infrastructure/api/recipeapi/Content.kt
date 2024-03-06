package com.tommannson.familycooking.infrastructure.api.recipeapi

import com.tommannson.familycooking.ui.screens.recipe.create.RecipeDto

data class Content(
    val content: List<RecipeDto> = emptyList()
)