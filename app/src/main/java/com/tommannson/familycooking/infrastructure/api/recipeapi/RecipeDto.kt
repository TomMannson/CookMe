package com.tommannson.familycooking.infrastructure.api.recipeapi

import androidx.annotation.Keep

@Keep
class RecipeDto(
    val name: String,
    val originalExtractedText: String,
    val recipeText: String? = null,
    val ingredientsText: String? = null,
    val toolsInfo: String? = null,
    val preparationTime: String? = null,
) {

    private val id: String? = null
}