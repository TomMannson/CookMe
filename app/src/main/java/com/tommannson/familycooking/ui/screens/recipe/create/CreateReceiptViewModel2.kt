package com.tommannson.familycooking.ui.screens.recipe.create

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommannson.familycooking.domain.LoadImageUseCase
import com.tommannson.familycooking.domain.TextRecognitionUseCase
import com.tommannson.familycooking.infrastructure.textRecognition.RecipeInfoSplitter
import com.tommannson.familycooking.ui.screens.recipe.create.state.UIEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class CreateReceiptViewModel2 @Inject constructor(
    private val loadImage: LoadImageUseCase,
    private val recognizeText: TextRecognitionUseCase,
) : ViewModel() {

    private val recipeInfoSplitter = RecipeInfoSplitter()
    private var operationJob: Job? = null
    //    private val _state = MutableStateFlow<RecipeCreationState>(RecipeCreationState.initial())
    //    val state = _state.asStateFlow()

    private val _events = MutableSharedFlow<UIEffect>()
    val event = _events.asSharedFlow()

    private val _onetimeEvents = MutableStateFlow<List<UIEffect>>(emptyList())
    val onetimeEvents = _onetimeEvents.asStateFlow()

    fun confirmEventHandling(effect: UIEffect) {
        _onetimeEvents.update { it.confirmHandling(effect) }
    }

    fun performAction(event: UIEffect) {
        when (event) {
            is ActionEffect.ImageLoadingStarted -> performImageLoading(event)
            is ActionEffect.ImageProcessingStarted -> performImageProcessing(event)
            else -> Timber.i("Unknown event: $event")
        }
    }

    private fun performImageLoading(action: ActionEffect.ImageLoadingStarted) {
        viewModelScope.launch {
            action.onResult(loadImage())
        }
    }

    private fun performImageProcessing(action: ActionEffect.ImageProcessingStarted) {
        viewModelScope.launch {
            action.onResult(recognizeText(action.uri))
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

sealed interface ActionEffect : UIEffect {

    data class ImageLoadingStarted(val onResult: (Result<Uri>) -> Unit) : ActionEffect
    data class ImageProcessingStarted(val uri: Uri, val onResult: (Result<String>) -> Unit) : ActionEffect
}

fun List<UIEffect>.addEffect(effect: UIEffect): List<UIEffect> {
    return toMutableList().apply { add(effect) }.toList()
}

fun List<UIEffect>.confirmHandling(effect: UIEffect): List<UIEffect> {
    return toMutableList().apply { this.remove(effect) }.toList()
}