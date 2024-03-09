package com.tommannson.familycooking.ui.screens.recipe.create.state

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tommannson.familycooking.ui.screens.recipe.create.ActionEffect
import com.tommannson.familycooking.ui.screens.recipe.create.CreateReceiptViewModel2
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.AcceptingImageStep
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.AcceptingUiEffect
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.ImageLoadingStep
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.InitializationStep
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.InitializationUiEffect
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.RecipeExtractionStep
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.RecipeTextFixingStep
import com.tommannson.familycooking.ui.state.UiAction
import timber.log.Timber

class StateMachine(
    private val viewModel: CreateReceiptViewModel2,
) {

    private var _currentStep: RecipeCreationState by mutableStateOf(RecipeCreationState.initial())
    val currentStep: RecipeCreationState get() = _currentStep

    fun loadImage() {
        val step = currentStep as? ImageLoadingStep
            ?: throw IllegalStateException("Step do not support imageLoading")

        step.loadImage()
            .map(::mapEffect)
            .forEach(viewModel::performAction)
    }

    fun acceptImage() {
        val step = currentStep as? AcceptingImageStep
            ?: throw IllegalStateException("Step do not support imageLoading")

        step.acceptImageForProcessing()
            .map(::mapEffect)
            .forEach(viewModel::performAction)
    }

    private fun onImageLoadingResult(result: Result<Uri>) {
        val step = (currentStep as? ImageLoadingStep)

        if (step == null) {
            Timber.v("You are not in loading process")
            return
        }

        step.notifyLoadingImageResult(result)
            ?.let { effect ->
                when (effect) {
                    is InitializationUiEffect.ImageLoadedCorrectly -> {
                        _currentStep = (step as InitializationStep).moveToExtractionScreen()
                    }

                    else -> error("Invalid effect: $effect")
                }
            }
    }

    private fun onImageProcessingResult(result: Result<String>) {
        val step = (currentStep as? AcceptingImageStep)

        if (step == null) {
            Timber.v("You are not in loading process")
            return
        }

        step.notifyProcessingResult(result)
            ?.let { effect ->
                when (effect) {
                    is AcceptingUiEffect.AcceptanceFinishedCorrectly -> {
                        _currentStep = (step as RecipeExtractionStep).moveToRecipeFixingScreen()
                    }

                    else -> error("Invalid effect: $effect")
                }
            }
    }

    private fun mapEffect(effect: UIEffect): UIEffect {
        return when (effect) {
            is InitializationUiEffect.ImageLoadingTrigger -> ActionEffect.ImageLoadingStarted(::onImageLoadingResult)
            is AcceptingUiEffect.AcceptanceTrigger -> ActionEffect.ImageProcessingStarted(
                currentStep.data.image!!.imageLocation,
                ::onImageProcessingResult
            )

            else -> error("Invalid effect: $effect")
        }
    }
}

fun InitializationStep.moveToExtractionScreen(): RecipeCreationState {
    return RecipeExtractionStep(
        dataConfig = this.data,
        textRecognitionActionConfig = UiAction.blockedByDefault(),
        tryAgainActionConfig = UiAction.blockedByDefault(),
    )
}

fun RecipeExtractionStep.moveToRecipeFixingScreen(): RecipeCreationState {
    return RecipeTextFixingStep(
        dataConfig = this.data,
        textRecognitionAction = UiAction.blockedByDefault(),
    )
}

