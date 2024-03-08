package com.tommannson.familycooking.ui.screens.recipe.create.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tommannson.familycooking.ui.screens.recipe.create.CreateReceiptViewModel2
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.state.UiAction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface RecipeFlowController {
    val currentStep: RecipeCreationState

    fun dispatchEffects(events: List<ActionEffect>)
}

class MockRecipeFlowController : RecipeFlowController {
    override val currentStep: RecipeCreationState = RecipeCreationState.initial(this)

    override fun dispatchEffects(events: List<ActionEffect>) {

    }
}

class RecipeFlowControllerImpl(private val viewModel: CreateReceiptViewModel2) :
    RecipeFlowController {

    private val initialStep = RecipeCreationState.InitializationStep(
        data = CollectedData(),
        imageLoadingAction = UiAction.activeByDefault(),
        manualRecipeCreation = UiAction.activeByDefault(),
        manager = this
    )
    private var textExtraction: RecipeCreationState.RecipeExtractionStep? = null

    private var _currentStep: RecipeCreationState by mutableStateOf(initialStep)
    override val currentStep: RecipeCreationState get() = _currentStep

    init {
        viewModel.onetimeEvents
            .onEach {
                it.forEach {
                    dispatchEffect(it)
                    viewModel.confirmEventHandling(it)
                }
            }.launchIn(GlobalScope)
    }


    override fun dispatchEffects(events: List<ActionEffect>) {
        events.forEach { event ->
            try {
                dispatchEffect(event)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun dispatchEffect(effects: ActionEffect) {
        when (effects) {
            is RecipeCreationState.InitializationStep.InitializationEffects.ImageLoading.Started -> viewModel.performImageLoading()
            is RecipeCreationState.InitializationStep.InitializationEffects.ImageLoading.Finished -> {
                moveToNext()
            }

            is RecipeCreationState.InitializationStep.InitializationEffects.ImageLoading.Success -> initialStep.notifyLoadedImage(
                effects.imageUri
            )

            is RecipeCreationState.InitializationStep.InitializationEffects.ImageLoading.Error -> initialStep.notifyLoadedImageError(
                effects.error
            )
        }
    }

    private fun moveToNext() {
        val current = currentStep

        if (current is RecipeCreationState.InitializationStep) {
            val newTextExtraction = RecipeCreationState.RecipeExtractionStep(
                data = CollectedData(),
                textRecognitionAction = UiAction.activeByDefault(),
                tryAgain = UiAction.activeByDefault(),
                manager = this
            )
            textExtraction = newTextExtraction
            _currentStep = newTextExtraction
        }

    }
}