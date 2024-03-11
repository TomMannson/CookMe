package com.tommannson.familycooking.ui.screens.recipe.create.aproach3.stateprovider

import com.tommannson.familycooking.domain.operations.RecipeCreatingOperation
import com.tommannson.familycooking.domain.operations.ProcessImageOperation
import com.tommannson.familycooking.domain.operations.SelectImageOperation
import javax.inject.Inject


internal class RecipeCreationRepository @Inject constructor(
    selectImage: SelectImageOperation,
    processImage: ProcessImageOperation,
    extractRecipe: RecipeCreatingOperation,
) {

    val selectedImageState = selectImage.state
    val processedImageState = processImage.state
    val recipeExtractionState = extractRecipe.state
}