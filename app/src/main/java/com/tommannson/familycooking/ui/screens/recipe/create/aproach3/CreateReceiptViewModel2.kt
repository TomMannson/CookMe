package com.tommannson.familycooking.ui.screens.recipe.create.aproach3

import androidx.lifecycle.viewModelScope
import com.tommannson.familycooking.ui.base.BaseViewModel
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.state.RecipeCreationState
import com.tommannson.familycooking.ui.screens.recipe.create.aproach3.stateprovider.RecipeCreationRepository
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.CollectedData
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ExtractedRecipe
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.ImageCollection
import com.tommannson.familycooking.ui.screens.recipe.create.state.data.TextExtraction
import com.tommannson.familycooking.ui.state.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CreateReceiptViewModel3 @Inject constructor(
    repository: RecipeCreationRepository,
    private val commandCreator: ActionCreator,
) : BaseViewModel() {

    private val accepting = MutableStateFlow(false)

    val state = combine(
        repository.selectedImageState,
        repository.processedImageState,
        repository.recipeExtractionState,
        accepting
    ) { state1, state2, state3, accepted ->
        if (state1.data != null && state2.data != null && state3.data != null && accepted) {
            RecipeCreationState.RecipeTextFixingStep(
                data = CollectedData(
                    image = ImageCollection(state1.data),
                    extractedText = TextExtraction(state2.data),
                    recipeData = ExtractedRecipe(
                        "",
                        state3.data.recipeContent,
                        state3.data.ingredientsContent
                    )
                ),
                recipeSubmittingAction = UiAction.activeByDefault()
            )
        } else if (state1.data != null && state2.data != null && state3.data != null) {
            RecipeCreationState.RecipeTextFixingStep(
                data = CollectedData(
                    image = ImageCollection(state1.data),
                    extractedText = TextExtraction(state2.data),
                    recipeData = ExtractedRecipe(
                        "",
                        state3.data.recipeContent,
                        state3.data.ingredientsContent
                    )
                ),
                recipeSubmittingAction = UiAction.activeByDefault()
            )
        } else if (state1.data != null && state2.data != null) {
            RecipeCreationState.RecipeTextExtractedStep(
                data = CollectedData(
                    image = ImageCollection(state1.data),
                    extractedText = TextExtraction(state2.data),
                ),
                recipeExtractionSubmittingAction = UiAction.activeByDefault()
            )
        } else if (state1.data != null) {
            RecipeCreationState.RecipeTextExtractionStep(
                data = CollectedData(
                    image = ImageCollection(
                        imageLocation = state1.data
                    )
                ),
                textRecognitionAction = if (state1.started) UiAction.blockedByDefault() else UiAction.activeByDefault()
            )
        } else {
            RecipeCreationState.initial()
        }
    }.stateInViewModel(RecipeCreationState.initial())

//    private val _state = MutableStateFlow<RecipeCreationState>(RecipeCreationState.initial())
//    val state = _state.asStateFlow()

    fun performAction(event: ScreenAction) {
        state.value.let { currentState ->
            viewModelScope.launch(Dispatchers.Main.immediate) {
                if (event is ScreenAction.Acceptance) {
                    accepting.value = true
                }

                commandCreator.create(currentState, event)
                    .logInvalidCommendExecution(currentState, event)
                    ?.invoke()
            }
        }
    }

    //
    //    fun restartProcess() {
    //        _state.update { it.restart() }
    //    }
    //
    //    fun submitReceipt() {
    //        //send to WS
    //        _state.update {
    //            ReceiptCreationStateOld(
    //                step = if (it.step is Step.ImageTextExtractionFinished) {
    //                    val stepInfo = it.step
    //                    val (recipeText, ingredientsText) = recipeInfoSplitter.splitRecipeInfo(stepInfo.extractedText)
    //
    //                    Step.ReceiptCreation(
    //                        imageUri = stepInfo.imageUri,
    //                        receiptName = "",
    //                        recipeContent = recipeText,
    //                        ingredientText = ingredientsText,
    //                        progressActive = false,
    //                        sendingResult = CreationResult.PENDING
    //                    )
    //                } else {
    //                    Step.ImageInfoError
    //                }
    //            )
    //        }
    //    }
    //
    //
    //    fun send(recipeName: String) {
    //        val step = _state.value.step as? Step.ReceiptCreation ?: return
    //        _state.update {
    //            ReceiptCreationStateOld(
    //                step.copy(progressActive = true)
    //            )
    //        }
    //
    //        viewModelScope.launch(Dispatchers.IO) {
    //            val dto = RecipeDto(
    //                name = recipeName,
    //                originalExtractedText = "",
    //                recipeText = step.recipeContent,
    //                ingredientsText = step.ingredientText,
    //            )
    //            val service = Api.createService()
    //
    //            val response = service.createRecipe(dto)
    //
    //            _state.update {
    //                if (response.isSuccessful) {
    //                    ReceiptCreationStateOld(
    //                        Step.Finished
    //                    )
    //                } else {
    //                    ReceiptCreationStateOld(
    //                        step.copy(
    //                            progressActive = false,
    //                            sendingResult = CreationResult.ERROR
    //                        ),
    //                    )
    //                }
    //            }
    //            delay(1000)
    //
    //            if (response.isSuccessful) {
    //                _events.emit(Unit)
    //            }
    //        }
    //    }
    //
    //
    //    fun performImageTextProcessing() {
    //        invokeOperation {
    //            val stepInfo = state.value.step as? Step.ImageProvided
    //                ?: return@invokeOperation
    //            showTextProcessingProgress()
    //            recognizeText.invoke(stepInfo.imageUri)
    //                .onSuccess { showTextRecognition(stepInfo.imageUri, it) }
    //                .onFailure(::showError)
    //        }
    //    }
    //
    //    private fun showError(th: Throwable) {
    //        _state.update { oldValue -> ReceiptCreationStateOld(Step.ImageInfoError) }
    //    }
    //
    //    private fun showImageLoadingProgress() {
    //        _state.update {
    //            it.updateScreenState()
    //        }
    //    }
    //
    //    private fun showTextProcessingProgress() {
    //        _state.update {
    //            it.updateScreenState(Sc)
    //            if (it.step is Step.ImageProvided) {
    //                ReceiptCreationStateOld(step = it.step.copy(progressActive = true))
    //            } else {
    //                ReceiptCreationStateOld(step = Step.NotInitialized())
    //            }
    //
    //        }
    //    }
    //
    //    private fun showLoadedImage(loadedImage: Uri) {
    //        _state.update { oldValue ->
    //            ReceiptCreationStateOld(
    //                Step.ImageProvided(imageUri = loadedImage)
    //            )
    //        }
    //    }
    //
    //    private fun showTextRecognition(loadedImage: Uri, extractedText: String) {
    //        _state.update { oldValue ->
    //            ReceiptCreationStateOld(
    //                Step.ImageTextExtractionFinished(
    //                    imageUri = loadedImage,
    //                    extractedText = extractedText
    //                )
    //            )
    //        }
    //    }
}


