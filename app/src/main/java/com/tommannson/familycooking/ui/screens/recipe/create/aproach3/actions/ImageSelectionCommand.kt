package com.tommannson.familycooking.ui.screens.recipe.create.aproach3.actions

import com.tommannson.familycooking.domain.operations.RecipeCreatingOperation
import com.tommannson.familycooking.domain.operations.ProcessImageOperation
import com.tommannson.familycooking.domain.operations.SelectImageOperation
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.state.RecipeCreationState
import timber.log.Timber

interface ExternalCommand : suspend () -> Unit

internal class ImageSelectionCommand(
    private val selectImage: SelectImageOperation,
) : ExternalCommand {
    override suspend fun invoke() {
        if (!selectImage.state.value.started) {
            selectImage.invoke(Unit)
        }
    }
}

internal class ProcessSelectedImageCommand(
    private val processImage: ProcessImageOperation,
    private val state: RecipeCreationState.RecipeTextExtractionStep
) : ExternalCommand {
    override suspend fun invoke() {
        val image = state.data.image
        if (!processImage.state.value.started && image != null) {
            processImage.invoke(image.imageLocation)
        } else {
            Timber.d("image is null")
        }
    }
}

internal class ProcessExtractedRecipeWithAICommand(
    private val extractRecipe: RecipeCreatingOperation,
    private val state: RecipeCreationState.RecipeTextFixingStep
) : ExternalCommand {
    override suspend fun invoke() {
        val recipeText = state.data.extractedText
        if (!extractRecipe.state.value.started && recipeText != null) {
            extractRecipe.invoke(recipeText.extractedText)
        } else {
            Timber.d("image is null")
        }
    }
}