package com.tommannson.familycooking.ui.screens.recipe.create.approuch4

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.tommannson.familycooking.infrastructure.textRecognition.RecipeContent
import com.tommannson.familycooking.infrastructure.textRecognition.RecipeInfoSplitter
import com.tommannson.familycooking.ui.screens.recipe.create.ProgressInfo
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ExtractedRecipe
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ImageCollection
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.TextExtraction
import com.tommannson.familycooking.ui.state.UiAction

@Immutable
data class RecipeCreationState(
    val step: ActiveStep,

    val imageLocation: Uri? = null,
    val extractedText: String? = null,
    val recipeData: RecipeInfoSplitter.SplittedContent? = null,

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

    val isReadyForSending get() = imageLocation != null
        && extractedText != null
        && recipeData != null

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