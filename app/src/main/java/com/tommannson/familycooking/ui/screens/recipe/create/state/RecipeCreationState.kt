package com.tommannson.familycooking.ui.screens.recipe.create.state


import android.net.Uri
import androidx.compose.runtime.Stable
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ImageCollection
import com.tommannson.familycooking.ui.state.ScreenProperties
import com.tommannson.familycooking.ui.state.UiAction
import com.tommannson.familycooking.ui.state.finishErrorRestartable
import com.tommannson.familycooking.ui.state.finishSuccessfully
import com.tommannson.familycooking.ui.state.startAction

@Stable
sealed interface RecipeCreationState {

    class InitializationStep(
        override var data: CollectedData,
        override var screenInfo: ScreenProperties = ScreenProperties.initial(),
        var imageLoadingAction: UiAction,
        var manualRecipeCreation: UiAction,
        private val manager: RecipeFlowController,
    ) : RecipeCreationState {
        fun loadImage() {
            val events = buildList {
                if (imageLoadingAction.canBeExecuted()) {
                    imageLoadingAction = imageLoadingAction.startAction()
                    add(InitializationEffects.ImageLoading.Started)
                }
            }

            manager.dispatchEffects(events)
        }

        fun notifyLoadedImage(uri: Uri) {
            if (imageLoadingAction.isWaitingForResult()) {
                imageLoadingAction.finishSuccessfully()
                data = data.copy(data.image?.copy(uri) ?: ImageCollection(uri))

                manager.dispatchEffects(listOf(InitializationEffects.ImageLoading.Finished))
            }
        }

        fun notifyLoadedImageError(error: Int) {
            if (imageLoadingAction.isWaitingForResult()) {
                imageLoadingAction.finishErrorRestartable(error)
            }
        }

        sealed interface InitializationEffects : ActionEffect {
            sealed interface ImageLoading : InitializationEffects {
                object Started : ImageLoading
                data class Success(val imageUri: Uri) : ImageLoading
                data class Error(val error: Int) : ImageLoading
                object Finished : ImageLoading
            }
        }
    }

    class RecipeExtractionStep(
        override val data: CollectedData,
        override val screenInfo: ScreenProperties = ScreenProperties.initial(),
        val textRecognitionAction: UiAction,
        val tryAgain: UiAction,
        private val manager: RecipeFlowController,
    ) : RecipeCreationState, Restartable {
        override fun restart() {
            manager.dispatchEffects(listOf(RecipeExtractionEffects.Restarted))
        }

        fun loadImageAgain() {
            manager.dispatchEffects(listOf(RecipeExtractionEffects.Restarted))
        }

        fun acceptImage() {
            manager.dispatchEffects(listOf(RecipeExtractionEffects.Restarted))
        }

        sealed interface RecipeExtractionEffects : ActionEffect {

            object Restarted : RecipeExtractionEffects
        }
    }

    data class RecipeTextFixingStep(
        override val data: CollectedData,
        override val screenInfo: ScreenProperties = ScreenProperties.initial(),
        val textRecognitionAction: UiAction
    ) : RecipeCreationState

    data class RecipeCreationStep(
        override val data: CollectedData,
        override val screenInfo: ScreenProperties = ScreenProperties.initial(),
        val textRecognitionAction: UiAction
    ) : RecipeCreationState

    val data: CollectedData
    val screenInfo: ScreenProperties

    companion object {
        fun initial(manager: RecipeFlowController) = InitializationStep(
            data = CollectedData(),
            imageLoadingAction = UiAction.activeByDefault(),
            manualRecipeCreation = UiAction.activeByDefault(),
            manager = manager
        )
    }
}

interface Restartable {
    fun restart()
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

interface ActionEffect

