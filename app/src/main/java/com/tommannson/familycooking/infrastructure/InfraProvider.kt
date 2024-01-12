package com.tommannson.familycooking.infrastructure

import androidx.activity.ComponentActivity
import com.tommannson.familycooking.infrastructure.cameraPicker.CameraPicker
import com.tommannson.familycooking.infrastructure.textRecognition.TextRecognizer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InfraProvider @Inject constructor(
    private val cameraPicker: CameraPicker,
    private val textRecognizer: TextRecognizer,
) {

    fun register(context: ComponentActivity) {
        with(cameraPicker) { context.registerCameraPicker() }
        with(textRecognizer) { context.registerTextRecognition() }
    }
}