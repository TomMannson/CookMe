package com.tommannson.familycooking.ui.screens.createreceipt

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tommannson.familycooking.domain.LoadImageUseCase
import com.tommannson.familycooking.domain.TextRecognitionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateReceiptViewModel @Inject constructor(
    private val loadImage: LoadImageUseCase,
    private val recognizeText: TextRecognitionUseCase
) : ViewModel() {

    private var operationJob: Job? = null
    private val _state = MutableStateFlow(ReceiptCreationState())
    val state = _state.asStateFlow()


    fun performImageLoading() {
        invokeOperation {
            loadImage()
                .onSuccess(::showLoadedImage)
                .onFailure(::showError)
        }
    }

    fun performImageTextProcessing() {
        invokeOperation {
            val stepInfo = state.value.step as? Step.ImageProvided
                ?: return@invokeOperation
            recognizeText.invoke(stepInfo.imageUri)
                .onSuccess { showTextRecognition(stepInfo.imageUri, it) }
                .onFailure(::showError)
        }
    }

    private fun showError(th: Throwable) {
        _state.update { oldValue -> ReceiptCreationState(Step.ImageInfoError) }
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
    val step: Step = Step.NotInitialized
)

sealed interface Step {
    data object NotInitialized : Step
    data class ImageProvided(
        val imageUri: Uri
    ) : Step

    data object ImageInfoError : Step

    data class ImageTextExtractionInProgress(
        val imageUri: Uri
    ) : Step

    data class ImageTextExtractionFinished(
        val imageUri: Uri,
        val extractedText: String
    ) : Step

    data class ImageTextExtractionError(
        val imageUri: Uri,
    ) : Step
}