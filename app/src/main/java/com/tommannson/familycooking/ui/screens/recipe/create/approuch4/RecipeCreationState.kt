package com.tommannson.familycooking.ui.screens.recipe.create.approuch4

import androidx.compose.runtime.Immutable
import com.tommannson.familycooking.ui.screens.recipe.create.ProgressInfo
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ExtractedRecipe
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ImageCollection
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.TextExtraction
import com.tommannson.familycooking.ui.state.UiAction

@Immutable
data class RecipeCreationState(
    val step: ActiveStep,

    val image: ImageCollection? = null,
    val extractedText: TextExtraction? = null,
    val recipeData: ExtractedRecipe? = null,

    val imageLoadingAction: UiAction,
    val manualCreationAction: UiAction,
    val textRecognitionAction: UiAction,
    val recipeExtractionSubmittingAction: UiAction,
    val recipeSubmittingAction: UiAction
) : ProgressInfo {

    override val progressActive: Boolean
        get() = manualCreationAction.state is UiAction.State.InProgress
                || imageLoadingAction.state is UiAction.State.InProgress
                || textRecognitionAction.state is UiAction.State.InProgress
                || recipeExtractionSubmittingAction.state is UiAction.State.InProgress
                || recipeSubmittingAction.state is UiAction.State.InProgress

    companion object {
        fun initial() = RecipeCreationState(
            ActiveStep.ImageSelection,
            null,
            null,
            null,
            UiAction.blockedByDefault(),
            UiAction.blockedByDefault(),
            UiAction.blockedByDefault(),
            UiAction.blockedByDefault(),
            UiAction.blockedByDefault(),
        )
    }

}

enum class ActiveStep {
    ImageSelection,
    ImageAcceptance,
    RecipeExtraction,
    RecipeFixing,
    Finished
}