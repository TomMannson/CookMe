package com.tommannson.familycooking.ui.screens.recipe.create.approuch4

import android.net.Uri
import com.tommannson.familycooking.domain.operations.ProcessImageOperation
import com.tommannson.familycooking.domain.operations.SelectImageOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TextExtractionHandler @Inject constructor(
    private val processImage: ProcessImageOperation,
) {
    val extractionState = MutableStateFlow<String?>(null)
    val operationStatus = processImage.state

    private val cancelledState = MutableStateFlow(false)

    suspend fun processImage(uri: Uri) {
        if (extractionState.value == null && operationStatus.value.started) {
            val data = processImage.invoke(uri)
                .first { it.finished }

            if (cancelledState.value) {
                cancelledState.value = false
                return
            }

            extractionState.value = data.getOrElse { error("invalid url") }
        }
    }

    suspend fun reset() {
        extractionState.value = null
        cancelledState.value = false
    }
}
