package com.tommannson.familycooking.ui.screens.recipe.create.approuch4

import android.net.Uri
import com.tommannson.familycooking.domain.operations.SelectImageOperation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ImageSelectionHandler @Inject constructor(
    private val selectImage: SelectImageOperation,
) {
    val imageSelectionState = MutableStateFlow<Uri?>(null)
    val operationStatus = selectImage.state

    private val cancelledState = MutableStateFlow(false)

    suspend fun selectImage() {
        if (imageSelectionState.value == null && !operationStatus.value.started) {
            cancelledState.value = false
            val data = selectImage.invoke(Unit)
                .first { it.finished }

            if (cancelledState.value) {
                cancelledState.value = false
                return
            }

            imageSelectionState.value = data.getOrElse { error("invalid url") }
        }
    }

    suspend fun reset() {
        imageSelectionState.value = null
        cancelledState.value = false
    }
}
