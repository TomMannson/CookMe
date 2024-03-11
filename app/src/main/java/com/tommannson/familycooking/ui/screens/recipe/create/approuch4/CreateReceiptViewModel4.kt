package com.tommannson.familycooking.ui.screens.recipe.create.approuch4

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.tommannson.familycooking.domain.operations.base.StatefulOperation
import com.tommannson.familycooking.infrastructure.api.recipeapi.RecipeDto
import com.tommannson.familycooking.infrastructure.textRecognition.RecipeInfoSplitter
import com.tommannson.familycooking.ui.base.BaseViewModel
import com.tommannson.familycooking.ui.base.UIEventHolder
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.ScreenAction
import com.tommannson.familycooking.ui.state.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
internal class CreateReceiptViewModel4 @Inject constructor(
    private val imageSelection: ImageSelectionHandler,
    private val textExtraction: TextExtractionHandler,
    private val recipeCreation: RecipeCreationHandler,
) : BaseViewModel() {

    private val eventHandler = UIEventHolder.getDefault()
    private val recipeInfoSplitter = RecipeInfoSplitter()
    private val recipeState = MutableStateFlow<RecipeInfoSplitter.SplittedContent?>(null)

    val events = eventHandler.events
    val state = combine(
        imageSelection.imageSelectionState,
        textExtraction.extractionState,
        recipeState,
        recipeCreation.recipeCreationState,
        combine(
            imageSelection.operationStatus,
            textExtraction.operationStatus,
        ) { one, two -> one to two }
    ) { selectedUri, textExtraction, recipeState, status, (imageLoading, recipeExtraction) ->
        RecipeCreationState(
            step = mapToActiveStep(
                uri = selectedUri,
                extractedText = textExtraction,
                recipe = recipeState
            ),
            imageLocation = selectedUri,
            extractedText = textExtraction,
            recipeData = recipeState,
            imageLoadingAction = imageLoading.mapToUiActionStateVisible(),
            manualCreationAction = imageLoading.mapToUiActionStateVisible(),
            textRecognitionAction = recipeExtraction.mapToUiActionStateVisible(),
            recipeExtractionSubmittingAction = UiAction.activeByDefault(),
            recipeSubmittingAction = UiAction.activeByDefault()
        )
    }.stateInViewModel(RecipeCreationState.initial())

    fun performAction(event: ScreenAction) {
        Timber.d("Event triggered: $event")
        when (event) {
            is ScreenAction.SelectImage -> selectImage()
            is ScreenAction.ProcessImage -> processImage()
            is ScreenAction.Acceptance -> acceptText()
            is ScreenAction.ProcessExtractedRecipe -> splitRecipe()
            is ScreenAction.Create -> sendRecipe(event.name)
        }
    }

    private fun selectImage() {
        viewModelScope.launch { imageSelection.selectImage() }
    }

    private fun processImage() {
        viewModelScope.launch {
            val imageLocation = state.value.imageLocation
            if (imageLocation != null) {
                textExtraction.processImage(imageLocation)
            }
        }
    }

    private fun acceptText() {
        viewModelScope.launch {
            val extractedText = state.value.extractedText
            if (extractedText != null) {
                val content = recipeInfoSplitter.splitRecipeInfo(extractedText)
            }
        }
    }

    private fun splitRecipe() {
        val extractedText = state.value.extractedText
        if (extractedText != null) {
            val recipeData = recipeInfoSplitter.splitRecipeInfo(extractedText)
            recipeState.value = recipeData
        } else {
            Timber.v("Illegal exported")
        }
    }

    private fun sendRecipe(recipeName: String) {
        viewModelScope.launch {
            val state = state.value
            val dto = RecipeDto(
                name = recipeName,
                originalExtractedText = state.extractedText!!,
                recipeText = state.recipeData!!.recipeContent,
                ingredientsText = state.recipeData.ingredientsContent,
            )

            recipeCreation.processImage(dto)
            recipeCreation.recipeCreationState.first { it != RecipeCreationHandler.Status.Idle }
            recipeCreation.acceptResponse()
        }
    }
}

private fun mapToActiveStep(uri: Uri?, extractedText: String?, recipe: RecipeInfoSplitter.SplittedContent?): ActiveStep {
    return when {
        recipe != null -> ActiveStep.RecipeFixing
        extractedText != null -> ActiveStep.RecipeExtraction
        uri != null -> ActiveStep.ImageAcceptance
        else -> ActiveStep.ImageSelection
    }
}

private fun StatefulOperation.State<*>.mapToUiActionStateVisible(): UiAction {
    return UiAction(
        state = if (started) UiAction.State.InProgress else UiAction.State.Idle,
        availability = if (started) UiAction.Availability.Blocked else UiAction.Availability.Available,
    )
}