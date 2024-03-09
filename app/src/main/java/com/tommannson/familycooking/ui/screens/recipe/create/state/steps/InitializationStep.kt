package com.tommannson.familycooking.ui.screens.recipe.create.state.steps

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tommannson.familycooking.ui.screens.recipe.create.state.RecipeCreationState
import com.tommannson.familycooking.ui.screens.recipe.create.state.UIEffect
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ImageCollection
import com.tommannson.familycooking.ui.state.ScreenProperties
import com.tommannson.familycooking.ui.state.UiAction
import com.tommannson.familycooking.ui.state.finishErrorRestartable
import com.tommannson.familycooking.ui.state.finishSuccessfully
import com.tommannson.familycooking.ui.state.startAction

interface ImageLoadingStep {

    fun loadImage(): List<InitializationUiEffect>
    fun notifyLoadingImageResult(result: Result<Uri>): InitializationUiEffect?
}

@Stable
interface ImageLoadingStepData {

    val data: CollectedData
    val manualCreation: UiAction
    val imageLoadingAction: UiAction

    val progressActive: Boolean
}

@Stable
class InitializationStep(
    override var data: CollectedData,
    override var screenInfo: ScreenProperties = ScreenProperties.initial(),
    imageLoadingActionConfig: UiAction,
    manualRecipeCreation: UiAction,
) : RecipeCreationState, ImageLoadingStep, ImageLoadingStepData {

    override var manualCreation: UiAction by mutableStateOf(manualRecipeCreation)
        private set
    override var imageLoadingAction: UiAction by mutableStateOf(imageLoadingActionConfig)
        private set
    override val progressActive: Boolean by derivedStateOf {
        manualCreation.state is UiAction.State.InProgress
            || imageLoadingAction.state is UiAction.State.InProgress
    }

    override fun loadImage(): List<InitializationUiEffect> {
        return buildList {
            if (imageLoadingAction.canBeExecuted()) {
                imageLoadingAction = imageLoadingAction.startAction()
                add(
                    InitializationUiEffect.ImageLoadingTrigger
                )
            }
        }
    }

    override fun notifyLoadingImageResult(result: Result<Uri>): InitializationUiEffect? {
        if (imageLoadingAction.isWaitingForResult()) {
            result.onSuccess { resultUri ->
                val newImageData = data.image?.copy(imageLocation = resultUri) ?: ImageCollection(resultUri)
                imageLoadingAction.finishSuccessfully()
                data = data.copy(image = newImageData)
                return InitializationUiEffect.ImageLoadedCorrectly
            }.onFailure {
                imageLoadingAction.finishErrorRestartable(15)
            }
        }
        return null
    }
}

sealed interface InitializationUiEffect : UIEffect {

    data object ImageLoadingTrigger : InitializationUiEffect
    data object ImageLoadedCorrectly : InitializationUiEffect
}