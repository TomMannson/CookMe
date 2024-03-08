package com.tommannson.familycooking.ui.screens.recipe.create.state.data

import android.net.Uri

data class CollectedData(
    val image: ImageCollection? = null,
    val extractedText: TextExtraction? = null,
    val recipeData: ExtractedRecipe? = null
)

data class ImageCollection(val imageLocation: Uri)

data class TextExtraction(val extractedText: String)

data class ExtractedRecipe(
    val receiptName: String,
    val recipeContent: String,
    val ingredientText: String,
)
