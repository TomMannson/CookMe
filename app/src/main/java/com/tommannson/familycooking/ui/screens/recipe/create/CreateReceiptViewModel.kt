package com.tommannson.familycooking.ui.screens.recipe.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommannson.familycooking.domain.LoadImageUseCase
import com.tommannson.familycooking.domain.TextRecognitionUseCase
import com.tommannson.familycooking.infrastructure.api.recipeapi.Api
import com.tommannson.familycooking.infrastructure.api.recipeapi.RecipeDto
import com.tommannson.familycooking.infrastructure.textRecognition.RecipeInfoSplitter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CreateReceiptViewModel @Inject constructor(
    private val loadImage: LoadImageUseCase,
    private val recognizeText: TextRecognitionUseCase
) : ViewModel() {

    private val recipeInfoSplitter = RecipeInfoSplitter()
    private var operationJob: Job? = null
    private val _state = MutableStateFlow(ReceiptCreationState())
    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<Unit>()
    val event = _events.asSharedFlow()

    fun performImageLoading() {
        invokeOperation {
            showImageLoadingProgress()
            loadImage()
                .onSuccess(::showLoadedImage)
                .onFailure(::showError)
        }
    }

    fun restartProcess() {
        _state.update { ReceiptCreationState() }
    }

    fun submitReceipt() {
        //send to WS
        _state.update {
            ReceiptCreationState(
                step = if (it.step is Step.ImageTextExtractionFinished) {
                    val stepInfo = it.step
                    val (recipeText, ingredientsText) = recipeInfoSplitter.splitRecipeInfo(stepInfo.extractedText)

                    Step.ReceiptCreation(
                        imageUri = stepInfo.imageUri,
                        receiptName = "",
                        recipeContent = recipeText,
                        ingredientText = ingredientsText,
                        progressActive = false,
                        sendingResult = CreationResult.PENDING
                    )
                } else {
                    Step.ImageInfoError
                }
            )
        }
    }


    fun send(recipeName: String) {
        val step = _state.value.step as? Step.ReceiptCreation ?: return
        _state.update {
            ReceiptCreationState(
                step.copy(progressActive = true)
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val dto = RecipeDto(
                name = recipeName,
                originalExtractedText = "",
                recipeText = step.recipeContent,
                ingredientsText = step.ingredientText,
            )
            val service = Api.createService()

            val response = service.createRecipe(dto)

            _state.update {
                if (response.isSuccessful) {
                    ReceiptCreationState(
                        Step.Finished
                    )
                } else {
                    ReceiptCreationState(
                        step.copy(
                            progressActive = false,
                            sendingResult = CreationResult.ERROR
                        ),
                    )
                }
            }
            delay(1000)

            if (response.isSuccessful) {
                _events.emit(Unit)
            }
        }
    }


    fun performImageTextProcessing() {
        invokeOperation {
            val stepInfo = state.value.step as? Step.ImageProvided
                ?: return@invokeOperation
            showTextProcessingProgress()
            recognizeText.invoke(stepInfo.imageUri)
                .onSuccess { showTextRecognition(stepInfo.imageUri, it) }
                .onFailure(::showError)
        }
    }

    private fun showError(th: Throwable) {
        _state.update { oldValue -> ReceiptCreationState(Step.ImageInfoError) }
    }

    private fun showImageLoadingProgress() {
        _state.update {
            ReceiptCreationState(
                step = Step.NotInitialized(progressActive = true)
            )
        }
    }

    private fun showTextProcessingProgress() {
        _state.update {
            if (it.step is Step.ImageProvided) {
                ReceiptCreationState(step = it.step.copy(progressActive = true))
            } else {
                ReceiptCreationState(step = Step.NotInitialized())
            }

        }
    }

    private fun showLoadedImage(loadedImage: Uri) {
        _state.update { oldValue ->
            ReceiptCreationState(
                Step.ImageProvided(imageUri = loadedImage)
            )
        }
    }

    private fun showTextRecognition(loadedImage: Uri, extractedText: String) {
        _state.update { oldValue ->
            ReceiptCreationState(
                Step.ImageTextExtractionFinished(
                    imageUri = loadedImage,
                    extractedText = extractedText
                )
            )
        }
    }

    private fun invokeOperation(operation: suspend () -> Unit) {
        val currentJob = operationJob
        if (currentJob != null && currentJob.isActive) {
            return
        }
        operationJob = viewModelScope.launch {
            operation()
        }
    }
}

data class ReceiptCreationState(
    val step: Step = Step.NotInitialized()
)

sealed interface Step {
    data class NotInitialized(
        override val progressActive: Boolean = false
    ) : Step, ProgressInfo
    data class ImageProvided(
        val imageUri: Uri,
        override val progressActive: Boolean = false
    ) : Step, ProgressInfo

    data object ImageInfoError : Step

    data class ImageTextExtractionFinished(
        val imageUri: Uri,
        val extractedText: String
    ) : Step

    data class ReceiptCreation(
        val receiptName: String,
        val recipeContent: String,
        val ingredientText: String,
        val imageUri: Uri,
        override val progressActive: Boolean,
        val sendingResult: CreationResult
    ) : Step, ProgressInfo

    data object Finished : Step

    data class ImageTextExtractionError(
        val imageUri: Uri,
    ) : Step
}

interface ProgressInfo {
    val progressActive: Boolean
}

enum class CreationResult { PENDING, SUCCESS, ERROR }