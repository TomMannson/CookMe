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

    fun register(activity: ComponentActivity) {
        with(cameraPicker) { activity.registerCameraPicker() }
        with(textRecognizer) { activity.registerTextRecognition() }
    }
}