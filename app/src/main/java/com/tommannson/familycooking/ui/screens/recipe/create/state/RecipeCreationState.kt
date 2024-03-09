package com.tommannson.familycooking.ui.screens.recipe.create.state


import androidx.compose.runtime.Stable
import com.tommannson.familycooking.ui.screens.recipe.create.ProgressInfo
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.InitializationStep
import com.tommannson.familycooking.ui.state.ScreenProperties
import com.tommannson.familycooking.ui.state.UiAction

@Stable
interface RecipeCreationState: ProgressInfo {

    data class RecipeCreationStep(
        override val data: CollectedData,
        override val screenInfo: ScreenProperties = ScreenProperties.initial(),
        val textRecognitionAction: UiAction,
        override val progressActive: Boolean
    ) : RecipeCreationState

    val data: CollectedData
    val screenInfo: ScreenProperties

    companion object {
        fun initial() = InitializationStep(
            data = CollectedData(),
            imageLoadingActionConfig = UiAction.activeByDefault(),
            manualRecipeCreation = UiAction.activeByDefault(),
        )
    }
}

interface DataUpdatable {
    fun updateData(data: CollectedData): RecipeCreationState
}

interface ScreenInfoUpdatable {
    fun updateScreenState(screenInfo: ScreenProperties): RecipeCreationState
}

interface ImageLoader {

    suspend fun loadImage(): Result<List<String>>

}

interface UIEffect

