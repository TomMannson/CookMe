package com.tommannson.familycooking.ui.screens.recipe.create.aproach3.state


import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.tommannson.familycooking.ui.screens.recipe.create.ProgressInfo
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.state.UiAction

@Stable
interface RecipeCreationState : ProgressInfo {
    @Immutable
    data class ImageSelectionStep(
        val data: CollectedData,
        val imageLoadingAction: UiAction,
        val manualCreationAction: UiAction,
    ) : RecipeCreationState {
        override val progressActive: Boolean
            get() = manualCreationAction.state is UiAction.State.InProgress
                    || imageLoadingAction.state is UiAction.State.InProgress
    }

    @Immutable
    data class RecipeTextExtractionStep(
        val data: CollectedData,
        val textRecognitionAction: UiAction,
    ) : RecipeCreationState {
        override val progressActive: Boolean
            get() = textRecognitionAction.state is UiAction.State.InProgress
    }

    @Immutable
    data class RecipeTextExtractedStep(
        val data: CollectedData,
        val recipeExtractionSubmittingAction: UiAction,
    ) : RecipeCreationState {
        override val progressActive: Boolean = false
    }

    @Immutable
    data class RecipeTextFixingStep(
        val data: CollectedData,
        val recipeSubmittingAction: UiAction,
    ) : RecipeCreationState {

        override val progressActive: Boolean
            get() =
                recipeSubmittingAction.state is UiAction.State.InProgress
    }

    @Immutable
    data class RecipeSubmittedStep(
        val data: CollectedData,
        val recipeSubmittingAction: UiAction,
    ) : RecipeCreationState {
        override val progressActive: Boolean
            get() =
                recipeSubmittingAction.state is UiAction.State.InProgress
    }

    companion object {
        fun initial() = ImageSelectionStep(
            data = CollectedData(),
            imageLoadingAction = UiAction.activeByDefault(),
            manualCreationAction = UiAction.activeByDefault(),
        )
    }
}

