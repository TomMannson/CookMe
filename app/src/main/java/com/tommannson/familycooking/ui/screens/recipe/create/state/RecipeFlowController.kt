package com.tommannson.familycooking.ui.screens.recipe.create.state

import com.tommannson.familycooking.ui.screens.recipe.create.CreateReceiptViewModel2
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.InitializationStep
import com.tommannson.familycooking.ui.screens.recipe.create.state.steps.RecipeExtractionStep
import com.tommannson.familycooking.ui.state.UiAction

interface RecipeFlowController {

    val currentStep: RecipeCreationState

    fun dispatchEffects(events: List<UIEffect>)
}

class RecipeFlowControllerImpl(private val viewModel: CreateReceiptViewModel2, override val currentStep: RecipeCreationState) :
    RecipeFlowController {

    private val initialStep = InitializationStep(
        data = CollectedData(),
        imageLoadingActionConfig = UiAction.activeByDefault(),
        manualRecipeCreation = UiAction.activeByDefault(),
    )
    private var textExtraction: RecipeExtractionStep? = null


    init {
//        viewModel.onetimeEvents
//            .onEach {
//                it.forEach {
//                    dispatchEffect(it)
//                    viewModel.confirmEventHandling(it)
//                }
//            }.launchIn(GlobalScope)
    }

    override fun dispatchEffects(events: List<UIEffect>) {
        events.forEach { event ->
            try {
                dispatchEffect(event)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun dispatchEffect(effects: UIEffect) {
//        when (effects) {
//            is InitializationUiEffect.ImageLoadingTrigger -> viewModel.performAction()
//        }
    }

    private fun moveToNext() {
        val current = currentStep

        if (current is InitializationStep) {
            val newTextExtraction = RecipeExtractionStep(
                dataConfig = CollectedData(),
                textRecognitionActionConfig = UiAction.activeByDefault(),
                tryAgainActionConfig = UiAction.activeByDefault(),
            )
            textExtraction = newTextExtraction
//            _currentStep = newTextExtraction
        }

    }
}