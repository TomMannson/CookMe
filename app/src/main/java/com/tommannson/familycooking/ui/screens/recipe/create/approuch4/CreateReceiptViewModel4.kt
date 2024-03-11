package com.tommannson.familycooking.ui.screens.recipe.create.approuch4

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.tommannson.familycooking.domain.operations.base.StatefulOperation
import com.tommannson.familycooking.infrastructure.api.recipeapi.RecipeDto
import com.tommannson.familycooking.infrastructure.textRecognition.RecipeInfoSplitter
import com.tommannson.familycooking.ui.base.BaseViewModel
import com.tommannson.familycooking.ui.base.UIEventHolder
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.ScreenAction
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ExtractedRecipe
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ImageCollection
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.TextExtraction
import com.tommannson.familycooking.ui.state.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class CreateReceiptViewModel4 @Inject constructor(
    private val imageSelection: ImageSelectionHandler,
    private val textExtraction: TextExtractionHandler,
    private val recipeCreation: RecipeCreationHandler,
) : BaseViewModel() {

    private val eventHandler = UIEventHolder.getDefault()
    private val recipeInfoSplitter = RecipeInfoSplitter()
    private val recipeState = MutableStateFlow<RecipeDto?>(null)

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
            image = selectedUri?.let { ImageCollection(it) },
            extractedText = textExtraction?.let { TextExtraction(it) },
            recipeData = recipeState?.let {
                ExtractedRecipe(
                    recipeState.name,
                    recipeState.recipeText!!,
                    recipeState.ingredientsText!!
                )
            },
            imageLoadingAction = imageLoading.mapToUiActionStateVisible(),
            manualCreationAction = imageLoading.mapToUiActionStateVisible(),
            textRecognitionAction = recipeExtraction.mapToUiActionStateVisible(),
            recipeExtractionSubmittingAction = UiAction.activeByDefault(),
            recipeSubmittingAction = UiAction.activeByDefault()
        )
    }.stateInViewModel(RecipeCreationState.initial())

    fun performAction(event: ScreenAction) {
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
            val image = state.value.image
            if (image != null) {
                textExtraction.processImage(image.imageLocation)
            }
        }
    }

    private fun acceptText() {
        viewModelScope.launch {
            val imageLocation = state.value.image
            if (imageLocation != null) {
                textExtraction.processImage(imageLocation.imageLocation)
            }
        }
    }

    private fun splitRecipe() {
        val extractedText = state.value.extractedText
        if (extractedText != null) {
            val content = recipeInfoSplitter.splitRecipeInfo(extractedText.extractedText)
        } else {
            Timber.v("Illegal exported")
        }
    }

    private fun sendRecipe(recipeName: String) {
        viewModelScope.launch {
            val state = state.value
            val dto = RecipeDto(
                name = recipeName,
                originalExtractedText = state.extractedText!!.extractedText,
                recipeText = state.recipeData!!.recipeContent,
                ingredientsText = state.recipeData.ingredientText,
            )

            recipeCreation.processImage(dto)
            recipeCreation.recipeCreationState.first { it != RecipeCreationHandler.Status.Idle }
            recipeCreation.acceptResponse()
        }
    }
}

private fun mapToActiveStep(uri: Uri?, extractedText: String?, recipe: RecipeDto?): ActiveStep {
    return when {
        uri != null -> ActiveStep.ImageAcceptance
        extractedText != null -> ActiveStep.RecipeExtraction
        recipe != null -> ActiveStep.RecipeFixing
        else -> ActiveStep.ImageSelection
    }
}

private fun StatefulOperation.State<*>.mapToUiActionStateVisible(): UiAction {
    return UiAction(
        state = if (started) UiAction.State.InProgress else UiAction.State.Idle,
        availability = if (started) UiAction.Availability.Blocked else UiAction.Availability.Available,
    )
}