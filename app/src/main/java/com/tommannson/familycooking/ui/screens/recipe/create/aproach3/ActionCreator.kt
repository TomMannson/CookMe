package com.tommannson.familycooking.ui.screens.recipe.create.aproach3

import com.tommannson.familycooking.domain.operations.ProcessImageOperation
import com.tommannson.familycooking.domain.operations.RecipeCreatingOperation
import com.tommannson.familycooking.domain.operations.SelectImageOperation
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.actions.ExternalCommand
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.actions.ImageSelectionCommand
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.actions.ProcessExtractedRecipeWithAICommand
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.actions.ProcessSelectedImageCommand
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.state.RecipeCreationState
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ActionCreator @Inject constructor(
    private val selectImage: SelectImageOperation,
    private val processImage: ProcessImageOperation,
    private val extractRecipe: RecipeCreatingOperation
) {
    internal fun create(state: RecipeCreationState, event: ScreenAction): ExternalCommand? {
        return if (state is RecipeCreationState.ImageSelectionStep) {
            if (event is ScreenAction.SelectImage) ImageSelectionCommand(selectImage) else null
        } else if (state is RecipeCreationState.RecipeTextExtractionStep) {
            if (event is ScreenAction.ProcessImage && state.data.image != null) {
                ProcessSelectedImageCommand(processImage, state)
            } else null
        } else if (state is RecipeCreationState.RecipeTextFixingStep) {
            if (event is ScreenAction.ProcessExtractedRecipe && state.data.image != null) {
                ProcessExtractedRecipeWithAICommand(extractRecipe, state)
            } else null
        } else {
            Timber.v("state is in proper for action execution")
            null
        }
    }
}

sealed interface ScreenAction {

    data object SelectImage : ScreenAction
    data object ProcessImage : ScreenAction
    data object ProcessExtractedRecipe : ScreenAction
    data object Acceptance : ScreenAction
    data class Create(val name: String) : ScreenAction
}